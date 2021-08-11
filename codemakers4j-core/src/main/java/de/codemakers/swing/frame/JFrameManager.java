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

package de.codemakers.swing.frame;

import de.codemakers.base.Standard;
import de.codemakers.io.file.AdvancedFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

//FIXME This is crap?! Maybe i need to create a completely new JFrameManager class... //Is this still true (post rework-logging) 2021-08-06?
public class JFrameManager extends JFrame {
    
    private static final Logger logger = LogManager.getLogger();
    
    private final JFrameTitle frameTitle;
    private final JFrameTitleFormatter frameTitleFormatter = JFrameTitleFormatter.createDefault();
    
    public JFrameManager() {
        this("");
    }
    
    public JFrameManager(String name) {
        this(name, "");
    }
    
    public JFrameManager(String name, String version) {
        super();
        this.frameTitle = new JFrameTitle(!Standard.RUNNING_JAR_IS_JAR, name, version);
        updateTitle();
        setName(name);
        setVersion(version);
    }
    
    public void setDefaultSettings() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateTitle();
    }
    
    public void show(Component c) {
        updateTitle();
        pack();
        setVisible(true);
        setLocationRelativeTo(c);
    }
    
    public void updateTitle() {
        setTitle(frameTitle.formatWithoutException(frameTitleFormatter));
    }
    
    public void setIconImage(String internPath) {
        setIconImage(new AdvancedFile(AdvancedFile.PREFIX_INTERN + internPath));
    }
    
    public void setIconImage(AdvancedFile advancedFile) {
        try (final InputStream inputStream = advancedFile.createInputStream()) {
            setIconImage(ImageIO.read(inputStream));
        } catch (Exception ex) {
            logger.error("Error while loading icon", ex);
        }
    }
    
    @Override
    public String getName() {
        return frameTitle.getName();
    }
    
    @Override
    public void setName(String name) {
        frameTitle.setName(name);
        updateTitle();
    }
    
    public String getVersion() {
        return frameTitle.getVersion();
    }
    
    public JFrameManager setVersion(String version) {
        frameTitle.setVersion(version);
        updateTitle();
        return this;
    }
    
    public JFrameManager addPrefix(Object object) {
        frameTitle.getPrefixes().add(object);
        updateTitle();
        return this;
    }
    
    public boolean removePrefix(Object object) {
        frameTitle.getPrefixes().remove(object);
        updateTitle();
        return frameTitle.getPrefixes().contains(object);
    }
    
    public JFrameManager addSuffix(Object object) {
        frameTitle.getSuffixes().add(object);
        updateTitle();
        return this;
    }
    
    public boolean removeSuffix(Object object) {
        frameTitle.getSuffixes().remove(object);
        updateTitle();
        return frameTitle.getSuffixes().contains(object);
    }
    
    @Override
    public String toString() {
        return "JFrameManager{" + "title=" + frameTitle + ", titleFormatter=" + frameTitleFormatter + '}';
    }
    
}
