/*
 *    Copyright 2018 - 2021 Paul Hagedorn (Panzer1119)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package de.codemakers.base.logging;

import de.codemakers.base.Standard;
import de.codemakers.base.entities.History;
import de.codemakers.base.logging.format.LogEventFormatter;
import de.codemakers.base.util.interfaces.Closeable;
import de.codemakers.base.util.interfaces.Finishable;
import de.codemakers.base.util.interfaces.Reloadable;
import de.codemakers.base.util.interfaces.Resettable;
import de.codemakers.i18n.I18nReloadEvent;
import de.codemakers.i18n.I18nReloadEventListener;
import de.codemakers.i18n.I18nUtil;
import de.codemakers.io.file.AdvancedFile;
import de.codemakers.io.streams.BufferedPipedOutputStream;
import de.codemakers.io.streams.PipedInputStream;
import de.codemakers.io.streams.PipedOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Console<S extends Console.ConsoleSettings<?>> implements Closeable, I18nReloadEventListener, Reloadable {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final String DEFAULT_ICON = "application_xp_terminal.png";
    public static final String DEFAULT_ICON_SETTINGS = "gear_in.png";
    public static final AdvancedFile DEFAULT_ICON_FILE = new AdvancedFile(Standard.ICONS_FAT_COW_32x32_FOLDER, DEFAULT_ICON);
    public static final AdvancedFile DEFAULT_ICON_SETTINGS_FILE = new AdvancedFile(Standard.ICONS_FAT_COW_32x32_FOLDER, DEFAULT_ICON_SETTINGS);
    
    public static final Level MINIMUM_LEVEL = Standard.RUNNING_JAR_IS_JAR ? LogLevel.INFO : LogLevel.DEBUG;
    
    public static final String DEFAULT_FONT_NAME = "Courier New";
    
    protected final List<LogEvent> logEvents = new CopyOnWriteArrayList<>();
    protected LogEventFormatter logEventFormatter = LogEventFormatter.createDefault();
    
    protected final Map<Level, Boolean> logLevelDisplayStatus = new ConcurrentHashMap<>();
    protected final Set<Level> displayedLogLevels = new CopyOnWriteArraySet<>();
    
    protected final JFrame frame = new JFrame("console");
    protected final JMenuBar menuBar = new JMenuBar();
    protected final JMenu menu_file = new JMenu("menu.file");
    protected final JMenuItem menuItem_reload = new JMenuItem("project.reload");
    //JSeparator
    protected final JMenuItem menuItem_settings = new JMenuItem("project.settings");
    //JSeparator
    protected final JMenuItem menuItem_saveAs = new JMenuItem("project.save_as");
    //JSeparator
    protected final JMenuItem menuItem_restart = new JMenuItem("project.restart");
    protected final JMenuItem menuItem_exit = new JMenuItem("project.exit");
    protected final JMenu menu_view = new JMenu("menu.view");
    //Output
    protected final JLabel label_displayedLogLevels = new JLabel("settings.displayed_log_levels");
    protected final JCheckBoxMenuItem[] checkBoxMenuItems_logLevels = Stream.of(LogLevel.USED_LEVELS).map((level) -> {
        final LogLevelStyle logLevelStyle = LogLevelStyle.ofLevel(level);
        final JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem(logLevelStyle.getLocalizedName());
        //checkBoxMenuItem.setSelected(Logger.getDefaultAdvancedLeveledLogger().getMinimumLogLevel().isThisLevelLessImportantOrEqual(level));
        //TODO Where to set/determine from what level should be shown?
        checkBoxMenuItem.setSelected(level.isMoreSpecificThan(MINIMUM_LEVEL));
        logLevelDisplayStatus.put(level, checkBoxMenuItem.isSelected());
        if (checkBoxMenuItem.isSelected()) {
            displayedLogLevels.add(level);
        } else {
            displayedLogLevels.remove(level);
        }
        checkBoxMenuItem.addActionListener((actionEvent) -> {
            logLevelDisplayStatus.put(level, checkBoxMenuItem.isSelected());
            if (checkBoxMenuItem.isSelected()) {
                displayedLogLevels.add(level);
            } else {
                displayedLogLevels.remove(level);
            }
            reloadWithoutException();
        });
        return checkBoxMenuItem;
    }).toArray(JCheckBoxMenuItem[]::new);
    protected final JTextPane textPane_output = new JTextPane();
    protected final JScrollPane scrollPane_output = new JScrollPane(textPane_output);
    //Input
    protected final JPanel panel_input = new JPanel();
    protected final JTextField textField_input = new JTextField();
    protected final JButton button_input = new JButton("input");
    protected final BufferedPipedOutputStream pipedOutputStream = new BufferedPipedOutputStream();
    protected final PipedInputStream pipedInputStream = new PipedInputStream();
    protected final int shutdownHookId = Standard.addShutdownHook(this::closeIntern);
    
    protected final ConsoleSettings<S> consoleSettings;
    
    protected final History<String> inputHistory = new History<>();
    
    public Console() {
        this(DEFAULT_ICON_FILE, DEFAULT_ICON_SETTINGS_FILE);
    }
    
    public Console(AdvancedFile iconAdvancedFile, AdvancedFile iconSettingsAdvancedFile) {
        init();
        initIconImage(iconAdvancedFile);
        initListeners();
        initStreams();
        consoleSettings = createConsoleSettings(iconSettingsAdvancedFile);
        setPreferredSize(new Dimension(1200, 600)); //TODO Testing only
        reloadWithoutException(); //TODO Good?
        reloadLanguage();
    }
    
    protected void write(int b) throws IOException {
        pipedOutputStream.write(b);
    }
    
    protected void write(byte[] data) throws IOException {
        pipedOutputStream.write(data);
    }
    
    protected void write(byte[] data, int off, int len) throws IOException {
        pipedOutputStream.write(data, off, len);
    }
    
    protected void flush() throws IOException {
        pipedOutputStream.flush();
    }
    
    public LogEventFormatter getLogEventFormatter() {
        return logEventFormatter;
    }
    
    public void setLogEventFormatter(LogEventFormatter logEventFormatter) {
        this.logEventFormatter = logEventFormatter;
    }
    
    protected abstract ConsoleSettings<S> createConsoleSettings(AdvancedFile iconAdvancedFile);
    
    /**
     * Handles input
     *
     * @param input Input
     *
     * @return If the {@link #textField_input} should be cleared
     *
     * @throws Exception
     */
    protected abstract boolean handleInput(String input) throws Exception; //FIXME Where is determined if this is an LogLevel.INPUT or an LogLevel.COMMAND?
    
    protected boolean handleInputWithoutException(String input) {
        try {
            return handleInput(input);
        } catch (Exception ex) {
            logger.error("Error while handling input \"" + input + "\"", ex);
            return true;
        }
    }
    
    @Override
    public boolean onEvent(I18nReloadEvent event) {
        reloadLanguage();
        return false;
    }
    
    protected void reloadLanguage() {
        // ResourceBundle console
        frame.setTitle(I18nUtil.getResourceBundleConsole().getString("console"));
        label_displayedLogLevels.setText(I18nUtil.getResourceBundleConsole().getString("settings.displayed_log_levels"));
        ((TitledBorder) scrollPane_output.getBorder()).setTitle(I18nUtil.getResourceBundleConsole().getString("output"));
        ((TitledBorder) panel_input.getBorder()).setTitle(I18nUtil.getResourceBundleConsole().getString("input"));
        // ResourceBundle ui
        menu_file.setText(I18nUtil.getResourceBundleUi().getString("menu.file"));
        menuItem_reload.setText(I18nUtil.getResourceBundleUi().getString("project.reload"));
        menuItem_settings.setText(I18nUtil.getResourceBundleUi().getString("project.settings"));
        menuItem_saveAs.setText(I18nUtil.getResourceBundleUi().getString("project.save_as"));
        menuItem_restart.setText(I18nUtil.getResourceBundleUi().getString("project.restart"));
        menuItem_exit.setText(I18nUtil.getResourceBundleUi().getString("project.exit"));
        menu_view.setText(I18nUtil.getResourceBundleUi().getString("menu.view"));
        button_input.setText(I18nUtil.getResourceBundleUi().getString("button.enter"));
        // ResourceBundle log_level
        for (int i = 0; i < LogLevel.USED_LEVELS.length; i++) {
            checkBoxMenuItems_logLevels[i].setText(LogLevel.getLocalizedName(LogLevel.USED_LEVELS[i]));
        }
        // Redraw
        frame.invalidate();
        frame.repaint();
        consoleSettings.reloadLanguage();
    }
    
    private void initStreams() {
        Standard.silentError(() -> pipedOutputStream.connect(pipedInputStream));
        pipedOutputStream.setThreadFunction((thread) -> {
            thread.setName(Console.class.getSimpleName() + "-" + PipedOutputStream.class.getSimpleName() + "-" + Thread.class.getSimpleName());
            return thread;
        });
    }
    
    private void initListeners() {
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }
            
            @Override
            public void windowClosing(WindowEvent e) {
                hide();
            }
            
            @Override
            public void windowClosed(WindowEvent e) {
            }
            
            @Override
            public void windowIconified(WindowEvent e) {
            }
            
            @Override
            public void windowDeiconified(WindowEvent e) {
            }
            
            @Override
            public void windowActivated(WindowEvent e) {
            }
            
            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        //TODO Add ActionListeners and KeyInputListeners for the (CheckBox)MenuItems and the TextField/Button
        //Output
        final ActionListener actionListener_reload = actionEvent -> reloadWithoutException();
        menuItem_reload.addActionListener(actionListener_reload);
        //Input
        textField_input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }
            
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                    setInputTextField(inputHistory.previous());
                } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                    setInputTextField(inputHistory.next());
                }
            }
            
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    button_input.doClick();
                }
            }
        });
        button_input.addActionListener((actionEvent) -> {
            final String input = textField_input.getText();
            if (handleInputWithoutException(input)) { //FIXME What do, when the last input is the exact same as this? Ignore the duplicate? A Set would not be useful, because maybe there is an input between 2 same inputs and then they should be both in the history? So make a toggle to enable a function that not saves the new input, if the exact last one is exact the same?
                inputHistory.add(input);
                textField_input.setText("");
            }
        });
    }
    
    private void init() {
        registerI18nReloadEventListener();
        menu_file.add(menuItem_settings);
        menu_file.add(new JSeparator());
        menu_file.add(menuItem_reload);
        menu_file.add(menuItem_saveAs);
        menu_file.add(new JSeparator());
        menu_file.add(menuItem_restart);
        menu_file.add(menuItem_exit);
        menuBar.add(menu_file);
        menu_view.add(label_displayedLogLevels);
        for (JCheckBoxMenuItem checkBoxMenuItem : checkBoxMenuItems_logLevels) {
            menu_view.add(checkBoxMenuItem); //TODO ActionListener or something similar?
        }
        menuBar.add(menu_view);
        frame.setJMenuBar(menuBar);
        frame.setLayout(new BorderLayout());
        textPane_output.setEditable(false);
        final Font font = textPane_output.getFont();
        textPane_output.setFont(new Font(DEFAULT_FONT_NAME, font.getStyle(), font.getSize()));
        scrollPane_output.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "output")); //TODO Is this looking good?
        frame.add(scrollPane_output, BorderLayout.CENTER);
        panel_input.setLayout(new BoxLayout(panel_input, BoxLayout.X_AXIS));
        panel_input.add(textField_input);
        panel_input.add(button_input);
        panel_input.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "input")); //TODO Is this looking good?
        frame.add(panel_input, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    private void initIconImage(AdvancedFile advancedFile) {
        try (final InputStream inputStream = advancedFile.createInputStream()) {
            frame.setIconImage(ImageIO.read(inputStream));
        } catch (Exception ex) {
            logger.error("Error while loading icon for frame", ex);
        }
    }
    
    protected void setInputTextField(String input) {
        textField_input.setText(input == null ? "" : input);
    }
    
    protected String formatLogEvent(LogEvent logEvent) {
        return logEventFormatter.formatWithoutException(logEvent);
    }
    
    protected void addLogEvent(LogEvent logEvent) {
        logEvents.add(logEvent.toImmutable());
        reloadWithoutException();
    }
    
    protected List<LogEvent> getLogEntriesFilteredByLogLevel() {
        return logEvents.stream().filter((entry) -> entry.getLevel() == null || displayedLogLevels.contains(entry.getLevel())).collect(Collectors.toList());
    }
    
    protected JFrame getFrame() {
        return frame;
    }
    
    public Console show() {
        return show(null);
    }
    
    public Console show(Component component) {
        frame.pack();
        frame.setLocationRelativeTo(component);
        frame.setVisible(true);
        return this;
    }
    
    public Console hide() {
        frame.setVisible(false);
        return this;
    }
    
    public Console setPreferredSize(Dimension dimension) {
        frame.setPreferredSize(dimension);
        return this;
    }
    
    @Override
    public void closeIntern() throws Exception {
        pipedInputStream.close();
        pipedOutputStream.close(); //TODO Is the order important? Close OutputStream before InputStream or vice versa?
        unregisterI18nReloadEventListener();
    }
    
    /*
    //Do not use this, but if we need that?
    @Override
    protected void finalize() throws Throwable {
        Standard.useWhenNotNull(Standard.removeShutdownHook(shutdownHookId), ToughRunnable::runWithoutException);
        super.finalize();
    }
    */
    
    public InputStream getInputStream() {
        return pipedInputStream;
    }
    
    public abstract static class ConsoleSettings<C extends ConsoleSettings<?>> implements Finishable<Boolean>, I18nReloadEventListener, Resettable {
        
        public ConsoleSettings() {
            registerI18nReloadEventListener();
        }
        
        protected abstract void showing();
        
        protected abstract void closing();
        
        public abstract C showAtConsole();
        
        public abstract C show(Component component);
        
        public abstract C hide();
        
        protected abstract void onAction();
        
        protected abstract boolean isEdited();
        
        protected abstract boolean isNotEdited();
        
        protected abstract void reloadLanguage();
    
        @Override
        public boolean onEvent(I18nReloadEvent event) {
            reloadLanguage();
            return false;
        }
        
    }
    
}
