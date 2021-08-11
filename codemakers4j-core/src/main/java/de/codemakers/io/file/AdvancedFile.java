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

package de.codemakers.io.file;

import de.codemakers.base.exceptions.NotImplementedRuntimeException;
import de.codemakers.base.os.OS;
import de.codemakers.base.os.OSUtil;
import de.codemakers.base.util.Require;
import de.codemakers.base.util.interfaces.Convertable;
import de.codemakers.base.util.interfaces.Copyable;
import de.codemakers.io.file.closeable.CloseablePath;
import de.codemakers.io.file.exceptions.FileNotUniqueSeparatorRuntimeException;
import de.codemakers.io.file.exceptions.FileProviderDoesNotSupportWriteOperationsRuntimeException;
import de.codemakers.io.file.exceptions.is.RelativeClassIsNullException;
import de.codemakers.io.file.exceptions.isnot.RelativeClassIsNotNullException;
import de.codemakers.io.file.filters.AdvancedFileFilter;
import de.codemakers.io.file.providers.FileProvider;
import de.codemakers.io.file.providers.JARProvider;
import de.codemakers.io.file.providers.ZIPProvider;
import de.codemakers.security.interfaces.Decryptor;
import de.codemakers.security.interfaces.Encryptor;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdvancedFile extends IFile<AdvancedFile, AdvancedFileFilter> implements Convertable<ExternFile>, Copyable {
    
    private static final Logger logger = LogManager.getLogger();
    
    public static final String FILE_SEPARATOR_WINDOWS_STRING = OSUtil.WINDOWS_HELPER.getFileSeparator();
    public static final String FILE_SEPARATOR_DEFAULT_STRING = OSUtil.DEFAULT_HELPER.getFileSeparator();
    public static final String FILE_SEPARATOR_CURRENT_STRING = OSUtil.CURRENT_OS_HELPER.getFileSeparator();
    public static final String FILE_SEPARATOR_NOT_CURRENT_STRING = FILE_SEPARATOR_CURRENT_STRING == FILE_SEPARATOR_WINDOWS_STRING ? FILE_SEPARATOR_DEFAULT_STRING : FILE_SEPARATOR_WINDOWS_STRING;
    public static final char FILE_SEPARATOR_WINDOWS_CHAR = OSUtil.WINDOWS_HELPER.getFileSeparatorChar();
    public static final char FILE_SEPARATOR_DEFAULT_CHAR = OSUtil.DEFAULT_HELPER.getFileSeparatorChar();
    public static final char FILE_SEPARATOR_CURRENT_CHAR = OSUtil.CURRENT_OS_HELPER.getFileSeparatorChar();
    public static final char FILE_SEPARATOR_NOT_CURRENT_CHAR = FILE_SEPARATOR_CURRENT_CHAR == FILE_SEPARATOR_WINDOWS_CHAR ? FILE_SEPARATOR_DEFAULT_CHAR : FILE_SEPARATOR_WINDOWS_CHAR;
    public static final String FILE_SEPARATOR_WINDOWS_REGEX = OSUtil.WINDOWS_HELPER.getFileSeparatorRegex();
    public static final String FILE_SEPARATOR_DEFAULT_REGEX = OSUtil.DEFAULT_HELPER.getFileSeparatorRegex();
    public static final String FILE_SEPARATOR_CURRENT_REGEX = (FILE_SEPARATOR_CURRENT_CHAR == FILE_SEPARATOR_WINDOWS_CHAR) ? FILE_SEPARATOR_WINDOWS_REGEX : FILE_SEPARATOR_DEFAULT_REGEX;
    public static final String FILE_SEPARATOR_NOT_CURRENT_REGEX = (FILE_SEPARATOR_CURRENT_CHAR != FILE_SEPARATOR_WINDOWS_CHAR) ? FILE_SEPARATOR_WINDOWS_REGEX : FILE_SEPARATOR_DEFAULT_REGEX;
    public static final String PATH_SEPARATOR = "/";
    public static final char PATH_SEPARATOR_CHAR = '/';
    public static final String PATH_SEPARATOR_REGEX = "/";
    
    public static final String PREFIX_INTERN = "intern:";
    public static final String PREFIX_EXTERN = "extern:";
    
    public static final List<FileProvider<AdvancedFile>> FILE_PROVIDERS = new CopyOnWriteArrayList<>();
    public static final ZIPProvider ZIP_PROVIDER = new ZIPProvider();
    public static final JARProvider JAR_PROVIDER = new JARProvider();
    
    public static boolean DEBUG = false;
    public static boolean DEBUG_TO_STRING = false;
    public static boolean DEBUG_TO_STRING_BIG = false;
    public static boolean DEBUG_FILE_PROVIDER = false;
    
    static {
        FILE_PROVIDERS.add(ZIP_PROVIDER);
        FILE_PROVIDERS.add(JAR_PROVIDER);
        //FIXME Disabled this, because it may causes very long loading times of this class. Maybe just do this in another Thread with Standard.async()? //UPDATE Enabled this using Standard.async(), but this needs to be improved, because the first call of an AdvancedFile may takes usage of a 3rd party FileProvider, which is not yet loaded... //UPDATE Disabled this again, because if you are running ultra short tests, this is still blocking the process
        /*
        Standard.async(() -> {
            final Set<Class<? extends FileProvider>> fileProviders = ReflectionUtil.getSubClasses(FileProvider.class);
            fileProviders.stream().filter((fileProvider) -> fileProvider.getAnnotation(AutoRegister.class) != null).forEach((fileProvider) -> {
                try {
                    final FileProvider fileProvider_ = fileProvider.newInstance();
                    if (fileProvider_ != null && !FILE_PROVIDERS.contains(fileProvider_)) {
                        FILE_PROVIDERS.add(fileProvider_);
                    }
                    if (DEBUG) {
                        logger.debug("Successfully auto registered FileProvider: " + fileProvider_);
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
            });
        });
        */
    }
    
    private String[] paths;
    private boolean init = false;
    private boolean windowsSeparator = OSUtil.getCurrentOS() == OS.WINDOWS;
    private boolean extern = true;
    private boolean absolute = true;
    private AdvancedFile parent;
    private FileProvider<AdvancedFile> fileProvider = null;
    //Only for relative intern files
    private Class<?> clazz;
    //Temp
    private transient String path;
    private transient Path path_;
    private transient URI uri;
    private transient URL url;
    private transient File file;
    
    public AdvancedFile(String... paths) {
        this(null, true, (paths.length == 0/* || (paths.length == 1 && paths[0].isEmpty())*/) ? new String[] {""} : paths);
    }
    
    public AdvancedFile(String name, String[] paths) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(paths);
        this.paths = Arrays.copyOf(paths, paths.length + 1);
        this.paths[paths.length] = name;
        this.extern = !checkInternAndCorrect();
        this.absolute = checkAbsoluteAndCorrect();
        checkInternAndRelative();
        init();
    }
    
    public AdvancedFile(AdvancedFile parent, String... paths) {
        this(parent, false, paths);
    }
    
    public AdvancedFile(AdvancedFile parent, boolean forceParent, String... paths) {
        if (forceParent) {
            this.parent = parent;
            if (parent != null) {
                this.windowsSeparator = parent.windowsSeparator;
                this.extern = parent.extern;
                this.absolute = parent.absolute;
                init = true;
            }
        } else {
            if (parent != null) {
                paths = Arrays.copyOf(paths, paths.length + 1);
                System.arraycopy(paths, 0, paths, 1, paths.length - 1);
                paths[0] = (parent.extern ? PREFIX_EXTERN : PREFIX_INTERN) + parent.getPath();
                this.windowsSeparator = parent.windowsSeparator;
                /*
                this.extern = parent.extern;
                this.absolute = parent.absolute;
                */
            }
        }
        this.paths = paths;
        if (!init) {
            this.extern = !checkInternAndCorrect();
            this.absolute = checkAbsoluteAndCorrect();
        }
        checkInternAndRelative();
        init();
    }
    
    public AdvancedFile(AdvancedFile parent, FileProvider<AdvancedFile> fileProvider, String... paths) {
        this.parent = parent;
        this.fileProvider = fileProvider;
        this.paths = paths;
        if (parent != null) {
            this.windowsSeparator = parent.windowsSeparator;
            this.extern = parent.extern;
            this.absolute = parent.absolute;
            init = true;
        } else {
            this.extern = !checkInternAndCorrect();
            this.absolute = checkAbsoluteAndCorrect();
            checkInternAndRelative();
        }
        init();
    }
    
    public AdvancedFile(File file) {
        this(file.getPath());
        this.file = file;
    }
    
    private AdvancedFile(String[] paths, boolean windowsSeparator, boolean extern, boolean absolute, AdvancedFile parent, FileProvider<AdvancedFile> fileProvider, Class<?> clazz) {
        this.paths = paths;
        this.windowsSeparator = windowsSeparator;
        this.extern = extern;
        this.absolute = absolute;
        this.parent = parent;
        this.fileProvider = fileProvider;
        this.clazz = clazz;
        this.init = true;
    }
    
    public static final FileProvider<AdvancedFile> getProvider(AdvancedFile parent, String name, AdvancedFile file, boolean exists) {
        Objects.requireNonNull(name);
        return FILE_PROVIDERS.stream()
                .filter((fileProvider) -> fileProvider.test(parent, name, file, exists))
                .max(Comparator.comparingInt((fileProvider) -> fileProvider.getPriority(parent, name)))
                .orElse(null);
    }
    
    public static final boolean couldBeProvided(AdvancedFile advancedFile) {
        if (advancedFile == null) {
            return false;
        }
        return FILE_PROVIDERS.stream().anyMatch((fileProvider) -> fileProvider.test(advancedFile));
    }
    
    public final boolean checkAbsoluteAndCorrect() {
        if (paths == null || paths.length == 0) {
            return false;
        }
        if (paths[0].startsWith(FILE_SEPARATOR_DEFAULT_STRING)) {
            paths[0] = paths[0].substring(FILE_SEPARATOR_DEFAULT_STRING.length());
            if (paths[0].isEmpty()) {
                System.arraycopy(paths, 1, paths, 0, paths.length - 1);
                paths = Arrays.copyOf(paths, paths.length - 1);
            }
            windowsSeparator = false;
            init = true;
            return true;
        } else if (paths[0].length() >= 2 && paths[0].charAt(1) == ':') {
            paths = Arrays.copyOf(paths, paths.length + 1);
            System.arraycopy(paths, 0, paths, 1, paths.length - 1);
            paths[0] = paths[0].substring(0, 2);
            paths[1] = paths[1].substring(3);
            windowsSeparator = true;
            init = true;
            return true;
        }
        return false;
    }
    
    public static final AdvancedFile intern(String... paths) {
        final AdvancedFile advancedFile = new AdvancedFile(paths);
        advancedFile.extern = false;
        return advancedFile;
    }
    
    static void listExternFilesRecursive(File folder, AdvancedFileFilter advancedFileFilter, List<AdvancedFile> advancedFiles) {
        if (advancedFileFilter != null) {
            Stream.of(folder.listFiles()).map((file) -> {
                if (file.isDirectory()) {
                    listExternFilesRecursive(file, advancedFileFilter, advancedFiles);
                }
                return file;
            }).map(AdvancedFile::new).filter(advancedFileFilter).forEach(advancedFiles::add);
        } else {
            Stream.of(folder.listFiles()).map((file) -> {
                if (file.isDirectory()) {
                    listExternFilesRecursive(file, advancedFileFilter, advancedFiles);
                }
                return file;
            }).map(AdvancedFile::new).forEach(advancedFiles::add);
        }
    }
    
    private final boolean checkInternAndCorrect() {
        if (paths.length > 0) {
            if (paths[0].startsWith(PREFIX_INTERN)) {
                paths[0] = paths[0].substring(PREFIX_INTERN.length());
                if (paths[0].isEmpty()) {
                    System.arraycopy(paths, 1, paths, 0, paths.length - 1);
                    paths = Arrays.copyOf(paths, paths.length - 1);
                }
                windowsSeparator = false;
                return true;
            } else if (paths[0].startsWith(PREFIX_EXTERN)) {
                paths[0] = paths[0].substring(PREFIX_EXTERN.length());
                if (paths[0].isEmpty()) {
                    System.arraycopy(paths, 1, paths, 0, paths.length - 1);
                    paths = Arrays.copyOf(paths, paths.length - 1);
                }
                return false;
            }
        }
        return false;
    }
    
    public String[] getPaths() {
        return paths;
    }
    
    public String getPathsCollected(String delimiter) {
        return Stream.of(paths).collect(Collectors.joining(delimiter));
    }
    
    public String getPathsCollected(String delimiter, String prefix, String suffix) {
        return Stream.of(paths).collect(Collectors.joining(delimiter, prefix, suffix));
    }
    
    final void reset() {
        resetPathString();
        resetPath();
        resetURI();
        resetURL();
        resetFile();
    }
    
    private final void checkInternAndRelative() {
        if (isIntern() && isRelative() && clazz == null) {
            final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            int i = 1;
            while ((i < stackTraceElements.length) && AdvancedFile.class.getName().equals(stackTraceElements[i].getClassName())) {
                i++;
            }
            if (i < stackTraceElements.length) {
                try {
                    clazz = AdvancedFile.class.getClassLoader().loadClass(stackTraceElements[i].getClassName());
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
        }
    }
    
    private final void init() { //TODO Test this
        boolean done = false;
        final List<String> paths_ = new ArrayList<>();
        for (String p : paths) {
            done = false;
            if (!init) {
                if (p.contains(FILE_SEPARATOR_WINDOWS_STRING)) {
                    paths_.addAll(Arrays.asList(p.split(FILE_SEPARATOR_WINDOWS_REGEX)));
                    windowsSeparator = true;
                    init = true;
                    done = true;
                }
                if (p.contains(FILE_SEPARATOR_DEFAULT_STRING)) {
                    if (init) {
                        throw new FileNotUniqueSeparatorRuntimeException(getPath() + " already contains a Windows file separator");
                    }
                    paths_.addAll(Arrays.asList(p.split(FILE_SEPARATOR_DEFAULT_REGEX)));
                    windowsSeparator = false;
                    init = true;
                    done = true;
                }
                if (!done) {
                    paths_.add(p);
                }
            } else {
                if (p.contains(FILE_SEPARATOR_WINDOWS_STRING)) {
                    if (windowsSeparator) {
                        paths_.addAll(Arrays.asList(p.split(FILE_SEPARATOR_WINDOWS_REGEX)));
                    } else {
                        throw new FileNotUniqueSeparatorRuntimeException(getPath() + " already contains an UNIX file separator");
                    }
                    done = true;
                }
                if (p.contains(FILE_SEPARATOR_DEFAULT_STRING)) {
                    if (windowsSeparator) {
                        throw new FileNotUniqueSeparatorRuntimeException(getPath() + " already contains a Windows file separator");
                    } else {
                        paths_.addAll(Arrays.asList(p.split(FILE_SEPARATOR_DEFAULT_REGEX)));
                    }
                    done = true;
                }
                if (!done) {
                    paths_.add(p);
                }
            }
        }
        init = true;
        String name = "";
        final List<String> temp = new ArrayList<>();
        for (String p : paths_) {
            temp.add(p);
            if (!name.isEmpty()) {
                name += FILE_SEPARATOR_DEFAULT_STRING;
            }
            name += p;
            final AdvancedFile advancedFile = new AdvancedFile(name.split(Matcher.quoteReplacement(FILE_SEPARATOR_DEFAULT_STRING)), windowsSeparator, extern, absolute, parent, null, clazz);
            final boolean exists = advancedFile.exists();
            final FileProvider<AdvancedFile> fileProvider = (!exists || advancedFile.isFile()) ? getProvider(parent, name, advancedFile, exists) : null;
            if (fileProvider != null) {
                fileProvider.processPaths(parent, name, temp);
                parent = new AdvancedFile(temp.toArray(new String[0]), windowsSeparator, extern, absolute, parent, fileProvider, clazz);
                clazz = null;
                temp.clear();
                name = "";
                if (DEBUG_FILE_PROVIDER) {
                    logger.debug("FOUND A  FILE PROVIDER FOR: \"" + p + "\": " + fileProvider);
                }
            } else {
                if (DEBUG_FILE_PROVIDER) {
                    logger.debug("FOUND NO FILE PROVIDER FOR: \"" + p + "\"");
                }
            }
        }
        if (temp.isEmpty() && parent != null) {
            set(parent);
        } else {
            paths = temp.toArray(new String[0]);
            temp.clear();
            name = "";
        }
        paths_.clear();
    }
    
    @Override
    public String getName() {
        if (paths.length == 0) {
            return "";
        }
        return paths[paths.length - 1];
    }
    
    @Override
    public String getPath() {
        if (path == null) {
            generatePath();
        }
        if (parent != null) {
            return parent.getPath() + getSeparatorChar() + path;
        }
        return path;
    }
    
    private final String generatePath() {
        path = Stream.of(paths).collect(Collectors.joining(getSeparator()));
        if (parent == null && !windowsSeparator && isAbsolute()) {
            path = getSeparatorChar() + path;
        }
        return path;
    }
    
    final void resetPathString() {
        path = null;
    }
    
    @Override
    public String getAbsolutePath() {
        if (isAbsolute()) { // Absolute path
            return getPath();
        } else {
            return getAbsoluteFile().getPath();
        }
    }
    
    @Override
    public String toExactString() {
        return (extern ? PREFIX_EXTERN : PREFIX_INTERN) + getAbsolutePath();
    }
    
    @Override
    public AdvancedFile getAbsoluteFile() {
        if (isAbsolute()) { // Absolute file
            return copy();
        } else if (parent != null) {
            final AdvancedFile file_absolute = copy();
            final AdvancedFile parent_root = file_absolute.getRootParent();
            final AdvancedFile parent_penultimate = file_absolute.getPenultimateParent();
            parent_penultimate.parent = parent_root.getAbsoluteFile();
            file_absolute.setAllAbsolute(true);
            return file_absolute;
        } else if (isIntern()) { // Relative intern file
            checkAndErrorIfRelativeClassIsNull(true);
            final String[] paths_prefixes = getPackagePathsFromClass();
            final String[] paths_ = new String[paths_prefixes.length + paths.length];
            if (paths_prefixes.length > 0) {
                System.arraycopy(paths_prefixes, 0, paths_, 0, paths_prefixes.length);
            }
            System.arraycopy(paths, 0, paths_, paths_prefixes.length, paths.length);
            return new AdvancedFile(paths_, windowsSeparator, extern, true, parent, fileProvider, null);
        } else { // Relative extern file
            return new AdvancedFile(toFile().getAbsolutePath()
                    .split(OSUtil.CURRENT_OS_HELPER.getFileSeparatorRegex()), windowsSeparator, extern, true, parent, fileProvider, clazz);
            
        }
    }
    
    @Override
    public AdvancedFile getParentFile() {
        if (paths.length <= 1) {
            return parent;
        }
        final String[] paths_ = new String[paths.length - 1];
        System.arraycopy(paths, 0, paths_, 0, paths_.length);
        return new AdvancedFile(paths_, windowsSeparator, extern, absolute, parent, fileProvider, clazz);
    }
    
    @Override
    public AdvancedFile getRoot() {
        String path_root = getAbsolutePath();
        final int index = path_root.indexOf(getSeparator());
        if (index >= 0) {
            path_root = path_root.substring(0, index);
        }
        return new AdvancedFile(path_root);
    }
    
    public AdvancedFile getParent() {
        return parent;
    }
    
    public AdvancedFile getRootParent() {
        if (parent == null) {
            return this;
        }
        return parent.getRootParent();
    }
    
    public AdvancedFile getPenultimateParent() {
        if (parent == null) {
            return this;
        } else if (parent.getParent() == null) {
            return this;
        }
        return parent.getPenultimateParent();
    }
    
    protected boolean isFileDirect() {
        return paths.length == 1;
    }
    
    protected boolean isFileProvided() {
        return fileProvider != null;
    }
    
    protected FileProvider<AdvancedFile> getFileProvider() {
        return fileProvider;
    }
    
    @Override
    public String getSeparator() {
        return windowsSeparator ? FILE_SEPARATOR_WINDOWS_STRING : FILE_SEPARATOR_DEFAULT_STRING;
    }
    
    @Override
    public char getSeparatorChar() {
        return windowsSeparator ? FILE_SEPARATOR_WINDOWS_CHAR : FILE_SEPARATOR_DEFAULT_CHAR;
    }
    
    @Override
    public String getSeparatorRegEx() {
        return windowsSeparator ? FILE_SEPARATOR_WINDOWS_REGEX : FILE_SEPARATOR_DEFAULT_REGEX;
    }
    
    @Override
    public boolean isFile() {
        if (parent != null) {
            return parent.isFile(this);
        }
        if (isExtern()) {
            return toFile().isFile();
        } else {
            if (paths.length == 0) {
                return false;
            }
            return toRealPath().closeWithoutException(Files::isRegularFile);
        }
    }
    
    boolean isFile(AdvancedFile file) {
        if (isFileProvided()) {
            try {
                return fileProvider.isFile(this, file, parent != null ? this::createInputStream : null);
            } catch (Exception ex) {
                logger.error(ex);
                return false;
            }
        } else {
            throw new NotImplementedRuntimeException();
        }
    }
    
    @Override
    public boolean isDirectory() {
        if (parent != null) {
            return parent.isDirectory(this);
        }
        if (isExtern()) {
            return toFile().isDirectory();
        } else {
            if (paths.length == 0) {
                return true;
            }
            return toRealPath().closeWithoutException(Files::isDirectory);
        }
    }
    
    @Override
    public boolean mayListFiles() {
        return isFileProvided() || isDirectory() || couldBeProvided(this);
    }
    
    boolean isDirectory(AdvancedFile file) {
        if (isFileProvided()) {
            try {
                return fileProvider.isDirectory(this, file, parent != null ? this::createInputStream : null);
            } catch (Exception ex) {
                logger.error(ex);
                return false;
            }
        } else {
            throw new NotImplementedRuntimeException();
        }
    }
    
    @Override
    public boolean exists() {
        if (parent != null) {
            return parent.exists(this);
        }
        if (isExtern()) {
            return toFile().exists();
        } else {
            if (isAbsolute()) {
                return AdvancedFile.class.getResource(getPath()) != null;
            } else {
                return clazz.getResource(getPath()) != null;
            }
        }
    }
    
    boolean exists(AdvancedFile file) {
        if (isFileProvided()) {
            try {
                return fileProvider.exists(this, file, this::createInputStream);
            } catch (Exception ex) {
                logger.error(ex);
                return false;
            }
        } else {
            throw new NotImplementedRuntimeException();
        }
    }
    
    private final void setAllAbsolute(boolean absolute) {
        this.absolute = absolute;
        if (parent != null) {
            parent.setAllAbsolute(absolute);
        }
    }
    
    @Override
    public boolean isAbsolute() {
        if (parent != null) {
            return parent.isAbsolute();
        }
        return absolute;
    }
    
    @Override
    public boolean isRelative() {
        if (parent != null) {
            return parent.isRelative();
        }
        return !absolute;
    }
    
    @Override
    public boolean isIntern() {
        if (parent != null) {
            return parent.isIntern();
        }
        return !extern;
    }
    
    private final void setAllExtern(boolean extern) {
        this.extern = extern;
        if (parent != null) {
            parent.setAllExtern(extern);
        }
    }
    
    @Override
    public boolean isExtern() {
        if (parent != null) {
            return parent.isExtern();
        }
        return extern;
    }
    
    @Override
    public Path toPath() {
        if (isIntern()) {
            throw new UnsupportedOperationException("Use toRealPath() for intern files");
        }
        if (path_ == null) {
            path_ = toFile().toPath();
        }
        return path_;
    }
    
    final void resetPath() {
        path_ = null;
    }
    
    public CloseablePath toRealPath() {
        if (isExtern()) {
            return new CloseablePath(null, toFile().toPath());
        } else {
            try {
                final URI uri = toURI();
                FileSystem fileSystem = null;
                Path myPath = null;
                if (uri.getScheme().equalsIgnoreCase("jar")) {
                    fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                    try {
                        myPath = fileSystem.getPath(getPath());
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                } else {
                    myPath = Paths.get(uri);
                }
                return new CloseablePath(fileSystem, myPath);
            } catch (Exception ex) {
                logger.error(ex);
                return null;
            }
        }
    }
    
    @Override
    public URI toURI() throws Exception {
        if (uri == null) {
            if (isIntern()) {
                uri = getNonNullClazz().getResource(getPath()).toURI();
            } else {
                uri = toFile().toURI();
            }
        }
        return uri;
    }
    
    final void resetURI() {
        uri = null;
    }
    
    @Override
    public URL toURL() throws Exception {
        if (url == null) {
            if (isIntern()) {
                url = getNonNullClazz().getResource(getPath());
            } else {
                url = toURI().toURL();
            }
        }
        return url;
    }
    
    final void resetURL() {
        url = null;
    }
    
    @Override
    public File toFile() {
        checkAndErrorIfIntern(true);
        if (file == null) {
            file = new File(getPath());
        }
        return file;
    }
    
    final void resetFile() {
        file = null;
        resetPath();
        if (isExtern()) {
            resetURI();
            resetURL();
        }
    }
    
    @Override
    public boolean mkdir() throws Exception {
        checkAndErrorIfIntern(true);
        checkAndErrorIfFile(checkAndErrorIfExisting(false));
        if (parent != null) {
            return mkdir(this);
        }
        if (isExtern()) {
            return toFile().mkdir();
        }
        throw new UnsupportedOperationException();
    }
    
    boolean mkdir(AdvancedFile file) throws Exception {
        if (isFileProvided()) {
            return fileProvider.mkdir(this, file);
        } else {
            throw new NotImplementedRuntimeException();
        }
    }
    
    @Override
    public boolean mkdirs() throws Exception {
        checkAndErrorIfIntern(true);
        checkAndErrorIfFile(checkAndErrorIfExisting(false));
        if (parent != null) {
            return mkdirs(this);
        }
        if (isExtern()) {
            return toFile().mkdirs();
        }
        throw new UnsupportedOperationException();
    }
    
    boolean mkdirs(AdvancedFile file) throws Exception {
        if (isFileProvided()) {
            return fileProvider.mkdirs(this, file);
        } else {
            throw new NotImplementedRuntimeException();
        }
    }
    
    @Override
    public boolean delete() throws Exception {
        checkAndErrorIfIntern(true);
        //checkAndErrorIfNotExisting(); //This should not throw an error, because maybe you only want to make sure, that a file is truly not existing any more
        if (parent != null) {
            return delete(this);
        }
        if (isExtern()) {
            return toFile().delete();
        }
        throw new UnsupportedOperationException();
    }
    
    boolean delete(AdvancedFile file) throws Exception {
        if (isFileProvided()) {
            return fileProvider.delete(this, file);
        } else {
            throw new NotImplementedRuntimeException();
        }
    }
    
    @Override
    public boolean createNewFile() throws Exception {
        checkAndErrorIfIntern(true);
        checkAndErrorIfDirectory(checkAndErrorIfExisting(false));
        if (parent != null) {
            return createNewFile(this);
        }
        if (isExtern()) {
            return toFile().createNewFile();
        }
        throw new UnsupportedOperationException();
    }
    
    boolean createNewFile(AdvancedFile file) throws Exception {
        if (isFileProvided()) {
            return fileProvider.createNewFile(this, file);
        } else {
            throw new NotImplementedRuntimeException();
        }
    }
    
    @Override
    public BufferedReader createBufferedReader() throws Exception {
        if (isExtern()) {
            checkAndErrorIfNotExisting(true);
            checkAndErrorIfNotFile(true);
            return new BufferedReader(new FileReader(toFile()));
        }
        return super.createBufferedReader();
    }
    
    @Override
    public InputStream createInputStream() throws Exception {
        //checkAndErrorIfNotExisting(true);
        //checkAndErrorIfNotFile(true);
        if (parent != null) {
            return parent.createInputStream(this);
        }
        if (isExtern()) {
            return new FileInputStream(toFile());
        } else {
            if (isAbsolute()) {
                return AdvancedFile.class.getResourceAsStream(getPath());
            } else {
                return clazz.getResourceAsStream(getPath());
            }
        }
    }
    
    InputStream createInputStream(AdvancedFile file) throws Exception {
        if (isFileProvided()) {
            return fileProvider.createInputStream(this, file, parent != null ? this::createInputStream : null);
        } else {
            throw new NotImplementedRuntimeException();
        }
    }
    
    @Override
    public byte[] readBytes() throws Exception {
        checkAndErrorIfNotExisting(true);
        checkAndErrorIfNotFile(true);
        if (parent != null) {
            return parent.readBytes(this);
        }
        if (isExtern()) {
            return Files.readAllBytes(toPath());
        } else {
            final InputStream inputStream = createInputStream();
            final byte[] data = IOUtils.toByteArray(inputStream);
            inputStream.close();
            return data;
        }
    }
    
    byte[] readBytes(AdvancedFile file) throws Exception {
        return fileProvider.readBytes(this, file, parent != null ? this::createInputStream : null);
    }
    
    @Override
    public BufferedWriter createBufferedWriter(boolean append) throws Exception {
        if (isExtern()) {
            checkAndErrorIfDirectory(checkAndErrorIfExisting(false));
            return new BufferedWriter(new FileWriter(toFile(), append));
        }
        return super.createBufferedWriter();
    }
    
    @Override
    public OutputStream createOutputStream(boolean append) throws Exception {
        checkAndErrorIfIntern(true);
        checkAndErrorIfDirectory(checkAndErrorIfExisting(false));
        if (parent != null) {
            return parent.createOutputStream(this, append);
        }
        if (isExtern()) {
            return new FileOutputStream(toFile(), append);
        }
        throw new UnsupportedOperationException();
    }
    
    OutputStream createOutputStream(AdvancedFile file, boolean append) throws Exception {
        checkAndErrorIfFileProviderDoesNotSupportWriteOperations(true, file);
        return fileProvider.createOutputStream(this, file, append);
    }
    
    @Override
    public boolean writeBytes(byte[] data) throws Exception {
        checkAndErrorIfIntern(true);
        checkAndErrorIfDirectory(checkAndErrorIfExisting(false));
        if (parent != null) {
            return parent.writeBytes(this, data);
        }
        if (isExtern()) {
            Files.write(toPath(), data);
            return true;
        }
        throw new UnsupportedOperationException();
    }
    
    boolean writeBytes(AdvancedFile file, byte[] data) throws Exception {
        checkAndErrorIfFileProviderDoesNotSupportWriteOperations(true, file);
        return fileProvider.writeBytes(this, file, data);
    }
    
    @Override
    public List<AdvancedFile> listFiles(boolean recursive) {
        checkAndErrorIfNotExisting(true);
        if (checkAndErrorIfNotDirectory(false)) {
            if (fileProvider == null) {
                checkAndErrorIfNotDirectory(true);
            }
            return listFiles(this, recursive);
        }
        if (parent != null) {
            return parent.listFiles(this, recursive);
        }
        if (isExtern()) {
            if (recursive) {
                final List<AdvancedFile> advancedFiles = new ArrayList<>();
                listExternFilesRecursive(toFile(), null, advancedFiles);
                return advancedFiles;
            } else {
                return Stream.of(toFile().listFiles()).map(AdvancedFile::new).collect(Collectors.toList());
            }
        } else {
            final List<AdvancedFile> advancedFiles = new ArrayList<>();
            final CloseablePath closeablePath = toRealPath();
            try {
                final Path myPath = closeablePath.getData();
                final int myPath_length = myPath.toString().length();
                if (recursive) {
                    Files.walk(myPath)
                            .skip(1)
                            .map((path_) -> path_.toString().substring(myPath_length + 1))
                            .map((path_) -> path_.endsWith(PATH_SEPARATOR) ? path_.substring(0, path_.length() - PATH_SEPARATOR.length()) : path_)
                            .map((path_) -> windowsSeparator ? path_.replaceAll(PATH_SEPARATOR_REGEX, FILE_SEPARATOR_WINDOWS_REGEX) : path_.replaceAll(FILE_SEPARATOR_WINDOWS_REGEX, PATH_SEPARATOR_REGEX))
                            .map((path_) -> new AdvancedFile(this, false, path_))
                            .forEach(advancedFiles::add);
                } else {
                    Files.walk(myPath, 1)
                            .skip(1)
                            .map((path_) -> path_.toString().substring(myPath_length + 1))
                            .map((path_) -> path_.endsWith(PATH_SEPARATOR) ? path_.substring(0, path_.length() - PATH_SEPARATOR.length()) : path_)
                            .map((path_) -> windowsSeparator ? path_.replaceAll(PATH_SEPARATOR_REGEX, FILE_SEPARATOR_WINDOWS_REGEX) : path_.replaceAll(FILE_SEPARATOR_WINDOWS_REGEX, PATH_SEPARATOR_REGEX))
                            .map((path_) -> new AdvancedFile(this, false, path_))
                            .forEach(advancedFiles::add);
                }
            } catch (Exception ex) {
                logger.error(ex);
            }
            closeablePath.closeWithoutException();
            return advancedFiles;
        }
    }
    
    List<AdvancedFile> listFiles(AdvancedFile file, boolean recursive) {
        try {
            return fileProvider.listFiles(this, file, recursive, parent != null ? this::createInputStream : null);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    @Override
    public List<AdvancedFile> listFiles(boolean recursive, AdvancedFileFilter advancedFileFilter) {
        if (advancedFileFilter == null) {
            return listFiles(recursive);
        }
        checkAndErrorIfNotExisting(true);
        if (checkAndErrorIfNotDirectory(false)) {
            if (fileProvider == null) {
                checkAndErrorIfNotDirectory(true);
            }
            return listFiles(this, recursive);
        }
        if (parent != null) {
            return parent.listFiles(this, recursive, advancedFileFilter);
        }
        if (isExtern()) {
            if (recursive) {
                final List<AdvancedFile> advancedFiles = new ArrayList<>();
                listExternFilesRecursive(toFile(), advancedFileFilter, advancedFiles);
                return advancedFiles;
            } else {
                return Stream.of(toFile().listFiles()).map(AdvancedFile::new).filter(advancedFileFilter).collect(Collectors.toList());
            }
        } else {
            final List<AdvancedFile> advancedFiles = new ArrayList<>();
            final CloseablePath closeablePath = toRealPath();
            try {
                final Path myPath = closeablePath.getData();
                final int myPath_length = myPath.toString().length();
                if (recursive) {
                    Files.walk(myPath)
                            .skip(1)
                            .map((path_) -> path_.toString().substring(myPath_length + 1))
                            .map((path_) -> path_.endsWith(PATH_SEPARATOR) ? path_.substring(0, path_.length() - PATH_SEPARATOR.length()) : path_)
                            .map((path_) -> windowsSeparator ? path_.replaceAll(PATH_SEPARATOR_REGEX, FILE_SEPARATOR_WINDOWS_REGEX) : path_.replaceAll(FILE_SEPARATOR_WINDOWS_REGEX, PATH_SEPARATOR_REGEX))
                            .map((path_) -> new AdvancedFile(this, true, path_))
                            .filter(advancedFileFilter)
                            .forEach(advancedFiles::add);
                } else {
                    Files.walk(myPath, 1)
                            .skip(1)
                            .map((path_) -> path_.toString().substring(myPath_length + 1))
                            .map((path_) -> path_.endsWith(PATH_SEPARATOR) ? path_.substring(0, path_.length() - PATH_SEPARATOR.length()) : path_)
                            .map((path_) -> windowsSeparator ? path_.replaceAll(PATH_SEPARATOR_REGEX, FILE_SEPARATOR_WINDOWS_REGEX) : path_.replaceAll(FILE_SEPARATOR_WINDOWS_REGEX, PATH_SEPARATOR_REGEX))
                            .map((path_) -> new AdvancedFile(this, true, path_))
                            .filter(advancedFileFilter)
                            .forEach(advancedFiles::add);
                }
            } catch (Exception ex) {
                logger.error(ex);
            }
            closeablePath.closeWithoutException();
            return advancedFiles;
        }
    }
    
    List<AdvancedFile> listFiles(AdvancedFile file, boolean recursive, AdvancedFileFilter advancedFileFilter) {
        try {
            return fileProvider.listFiles(this, file, recursive, parent != null ? this::createInputStream : null, advancedFileFilter);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    @Override
    public AdvancedFile copy() {
        return new AdvancedFile(paths, windowsSeparator, extern, absolute, parent, fileProvider, clazz);
    }
    
    @Override
    public void set(Copyable copyable) {
        Objects.requireNonNull(copyable);
        final AdvancedFile advancedFile = Require.clazz(copyable, AdvancedFile.class);
        this.paths = advancedFile.paths;
        this.windowsSeparator = advancedFile.windowsSeparator;
        this.extern = advancedFile.extern;
        this.absolute = advancedFile.absolute;
        this.parent = advancedFile.parent;
        this.fileProvider = advancedFile.fileProvider;
        this.clazz = advancedFile.clazz;
    }
    
    @Override
    public String toString() {
        if (DEBUG_TO_STRING) {
            if (DEBUG_TO_STRING_BIG) {
                return getClass().getSimpleName() + "{" + "paths=" + Arrays.toString(paths) + " (" + paths.length + ")" + ", init=" + init + ", windowsSeparator=" + windowsSeparator + ", extern=" + extern + ", absolute=" + absolute + ", fileProvider=" + fileProvider + ", clazz=" + clazz + ", path='" + path + '\'' + ", path_=" + path_ + ", uri=" + uri + ", url=" + url + ", file=" + file + ", parent=" + parent + '}';
            } else {
                return getClass().getSimpleName() + "{" + "paths=" + Arrays.toString(paths) + " (" + paths.length + ")" + ", windowsSeparator=" + windowsSeparator + ", extern=" + extern + ", absolute=" + absolute + ", fileProvider=" + fileProvider + ", clazz=" + clazz + ", parent=" + parent + '}';
            }
        }
        return getPath();
    }
    
    public final Class<?> getClazz() {
        return clazz;
    }
    
    public final AdvancedFile setClazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }
    
    public final Class<?> getNonNullClazz() {
        if (clazz == null) {
            return getClass();
        }
        return clazz;
    }
    
    private boolean checkAndErrorIfRelativeClassIsNull(boolean throwException) {
        if (clazz == null) {
            if (throwException) {
                throw new RelativeClassIsNullException(getPath() + " has no relative class");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    private boolean checkAndErrorIfRelativeClassIsNotNull(boolean throwException) {
        if (clazz != null) {
            if (throwException) {
                throw new RelativeClassIsNotNullException(getPath() + " has an relative class");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    private boolean checkAndErrorIfFileProviderDoesNotSupportWriteOperations(boolean throwException, AdvancedFile advancedFile) {
        if (parent != null && (fileProvider != null && !fileProvider.canWrite(this, advancedFile))) {
            if (throwException) {
                throw new FileProviderDoesNotSupportWriteOperationsRuntimeException(getPath() + " can not be written to");
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    
    private <R> R usePackageIfNotNull(Function<Package, R> function, R defaultValue) {
        if (function != null && getNonNullClazz().getPackage() != null) {
            return function.apply(getNonNullClazz().getPackage());
        }
        return defaultValue;
    }
    
    private String getPackageNameFromClass() {
        return usePackageIfNotNull((p) -> p.getName(), "");
    }
    
    private String getPackagePathFromClass() {
        return usePackageIfNotNull((p) -> p.getName().replaceAll("\\.", FILE_SEPARATOR_DEFAULT_REGEX), "");
    }
    
    private String[] getPackagePathsFromClass() {
        return usePackageIfNotNull((p) -> p.getName().split("\\."), new String[0]);
    }
    
    @Override
    public ExternFile convert(Class<ExternFile> clazz) {
        checkAndErrorIfIntern(true);
        return new ExternFile(toFile());
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final AdvancedFile that = (AdvancedFile) other;
        return init == that.init && windowsSeparator == that.windowsSeparator && extern == that.extern && absolute == that.absolute && Arrays.equals(paths, that.paths) && Objects
                .equals(parent, that.parent) && ((fileProvider == null && that.fileProvider == null) || ((fileProvider != null && that.fileProvider != null) && Objects
                .equals(fileProvider.getClass(), that.fileProvider.getClass()))) && Objects.equals(clazz, that.clazz);
    }
    
    @Override
    public int hashCode() {
        int result = Objects.hash(windowsSeparator, extern, absolute, parent, fileProvider == null ? null : fileProvider.getClass(), clazz);
        result = 31 * result + Arrays.hashCode(paths);
        return result;
    }
    
    @Override
    public AdvancedFile encryptThis(Encryptor encryptor) throws Exception {
        Objects.requireNonNull(encryptor);
        writeBytes(encryptor.encryptWithoutException(readBytes())); //TODO Make this via Streams, because loading big files in the memory is not efficient //STOP Could be difficult, because this is encrypting the file itself, and it is not a good idea to create an InputStream and OutputStream at the same time for a file
        return this;
    }
    
    @Override
    public AdvancedFile decryptThis(Decryptor decryptor) throws Exception {
        Objects.requireNonNull(decryptor);
        writeBytes(decryptor.decryptWithoutException(readBytes())); //TODO Make this via Streams, because loading big files in the memory is not efficient //STOP Could be difficult, because this is encrypting the file itself, and it is not a good idea to create an InputStream and OutputStream at the same time for a file
        return this;
    }
    
}
