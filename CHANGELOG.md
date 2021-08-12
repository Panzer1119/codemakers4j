# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

### [0.20.9](https://github.com/Panzer1119/codemakers4j/compare/v0.20.8...v0.20.9) (2021-08-12)

### [0.20.8](https://github.com/Panzer1119/codemakers4j/compare/v0.20.7...v0.20.8) (2021-08-11)

### [0.20.7](https://github.com/Panzer1119/codemakers4j/compare/v0.20.6...v0.20.7) (2021-08-11)

### [0.20.6](https://github.com/Panzer1119/codemakers4j/compare/v0.20.5...v0.20.6) (2021-08-11)

### [0.20.5](https://github.com/Panzer1119/codemakers4j/compare/v0.20.4...v0.20.5) (2021-08-11)

### [0.20.4](https://github.com/Panzer1119/codemakers4j/compare/v0.20.3...v0.20.4) (2021-08-11)

### [0.20.3](https://github.com/Panzer1119/codemakers4j/compare/v0.20.2...v0.20.3) (2021-08-11)

### [0.20.2](https://github.com/Panzer1119/codemakers4j/compare/v0.20.1...v0.20.2) (2021-08-11)

### [0.20.1](https://github.com/Panzer1119/codemakers4j/compare/v0.20.0...v0.20.1) (2021-08-11)


### Bug Fixes

* rename directories from cjp... to codemakers4j... ([d4c82ac](https://github.com/Panzer1119/codemakers4j/commit/d4c82ac1861acec6232d872f7f670a8d2be47a53))

## [0.20.0](https://github.com/Panzer1119/codemakers4j/compare/v0.16.14...v0.20.0) (2021-08-11)


### Bug Fixes

* **i18n:** set default locales when calling setLocale on I18nUtil.java ([8388a6a](https://github.com/Panzer1119/codemakers4j/commit/8388a6ab617159d561b7e414352ea41feb6302b7))

### [0.1.11](https://github.com/Panzer1119/codemakers4j/compare/v0.1.10...v0.1.11) (2021-08-06)

### [0.1.10](https://github.com/Panzer1119/codemakers4j/compare/v0.16.12...v0.1.10) (2021-08-06)

### [0.1.9](https://github.com/Panzer1119/codemakers4j/compare/v0.16.11...v0.1.9) (2021-08-06)

### [0.1.8](https://github.com/Panzer1119/codemakers4j/compare/v0.16.10...v0.1.8) (2021-08-06)

### [0.1.7](https://github.com/Panzer1119/codemakers4j/compare/v0.1.6...v0.1.7) (2021-08-05)


### Features

* add setBasicProperty and setSubstitution to HibernateProperties.java ([bbb6e4d](https://github.com/Panzer1119/codemakers4j/commit/bbb6e4d5ebb062a2c2a5b1775449ab4e6c0e9b6b))

### [0.1.6](https://github.com/Panzer1119/codemakers4j/compare/v0.1.5...v0.1.6) (2021-08-04)


### Bug Fixes

* only rollback a transaction if an error occurred in HibernateUtil.java ([212005f](https://github.com/Panzer1119/codemakers4j/commit/212005f0db54bf37470e7d1eec2d7410d09649e0))

### [0.1.5](https://github.com/Panzer1119/codemakers4j/compare/v0.1.4...v0.1.5) (2021-08-04)


### Bug Fixes

* make MinIOConnector.java constructor public ([efb9451](https://github.com/Panzer1119/codemakers4j/commit/efb94519e5af2db6b8ce2abe570d5ea8f56db9ca))

### [0.1.4](https://github.com/Panzer1119/codemakers4j/compare/v0.1.3...v0.1.4) (2021-08-04)


### Bug Fixes

* edit addOrUpgradeById methods in DatabaseConnector.java and HibernateUtil.java ([934e657](https://github.com/Panzer1119/codemakers4j/commit/934e657ab3c17e6ec1e477147bd52074245c0619))

### [0.1.3](https://github.com/Panzer1119/codemakers4j/compare/v0.16.6...v0.1.3) (2021-08-04)

### [0.1.2](https://github.com/Panzer1119/codemakers4j/compare/v0.1.1...v0.1.2) (2021-08-03)


### Bug Fixes

* create private constructor in HibernateUtil.java to hide the implicit public one ([c18188b](https://github.com/Panzer1119/codemakers4j/commit/c18188b30f970393c436f46a2ef80fddc2b927fe))
* remove unused class parameter from addOrUpgradeById methods ([0297eef](https://github.com/Panzer1119/codemakers4j/commit/0297eef56a215ee01515f2373545a90fcafa73cf))

### [0.1.1](https://github.com/Panzer1119/codemakers4j/compare/v0.16.5...v0.1.1) (2021-08-03)


### Features

* add checkParameter method to ObjectStorageConnector.java ([0b183d6](https://github.com/Panzer1119/codemakers4j/commit/0b183d6116f1e8c49680e47d075dcbf4600f5dea))
* create DatabaseConnector.java ([94f5302](https://github.com/Panzer1119/codemakers4j/commit/94f5302dbc065d41de53926ec1e4f746a8d044e1))
* create HibernateProperties.java, add templates hibernate-mysql.properties and hibernate-postgresql.properties ([2343119](https://github.com/Panzer1119/codemakers4j/commit/23431198ad367a6f8d37c9eb42f335ec3c20084c))
* create HibernateUtil.java ([a5229cb](https://github.com/Panzer1119/codemakers4j/commit/a5229cb99fec344ab00b8d22fee69a872004a567))
* create IEntity.java ([e6febb6](https://github.com/Panzer1119/codemakers4j/commit/e6febb68dfebe06d93e747492a2d91d125656198))
* create JsonBPostgreSQLDialect.java ([19660d8](https://github.com/Panzer1119/codemakers4j/commit/19660d8119787ab4fd2b472ebd930f407e0e9f4c))
* create JsonType.java ([a3dced8](https://github.com/Panzer1119/codemakers4j/commit/a3dced87c8b6cbcd4e59e6c384a8b8cab9ba414d))
* create MinIOConnector.java ([b1a0133](https://github.com/Panzer1119/codemakers4j/commit/b1a0133e879cf69e27decee1a89da8e9df2b1be6))
* create ObjectStorageConnector.java ([fc7c5be](https://github.com/Panzer1119/codemakers4j/commit/fc7c5be5d1d32efcf0533516de7a8dcd2ed70600))
* implement add and set methods in DatabaseConnector.java ([d63ab36](https://github.com/Panzer1119/codemakers4j/commit/d63ab36085a1b5c560eaa8baedeebc670f5c3f93))
* implement add and set methods in HibernateUtil.java ([adf490c](https://github.com/Panzer1119/codemakers4j/commit/adf490cbabfe71a2a6f9e6ee1f434f608bcff904))
* implement addOrSet and delete methods in DatabaseConnector.java ([b345bf5](https://github.com/Panzer1119/codemakers4j/commit/b345bf57d866d4455e19b398ffe8555c13a3e8fe))
* implement addOrSet and delete methods in HibernateUtil.java ([0c13f9c](https://github.com/Panzer1119/codemakers4j/commit/0c13f9ce842efb3787475cc060585cfff9268f7a))
* implement addOrUpgradeById methods in DatabaseConnector.java ([af2e4a2](https://github.com/Panzer1119/codemakers4j/commit/af2e4a225f6a931cc4917e3100177b33721f9d95))
* implement addOrUpgradeById methods in HibernateUtil.java ([d9367ad](https://github.com/Panzer1119/codemakers4j/commit/d9367ad7b6455afd405e8e695b27a465f71d9671))
* implement get/getAll/getWhere/getAllWhere methods in DatabaseConnector.java ([93a4788](https://github.com/Panzer1119/codemakers4j/commit/93a4788c8f252945907bb4b847e5f5738d342e7a))
* implement get/getAll/getWhere/getAllWhere methods in HibernateUtil.java ([1222bc8](https://github.com/Panzer1119/codemakers4j/commit/1222bc8848aa2a1a7a333d453bc85517afeffe54))
* implement useSession and processSession in DatabaseConnector.java ([126be9f](https://github.com/Panzer1119/codemakers4j/commit/126be9f2ecbaf0689fdbefd591a0ab61fa622f66))
* implement useSession and processSession in HibernateUtil.java ([8aefd92](https://github.com/Panzer1119/codemakers4j/commit/8aefd92e9486f458e6b9354f0b0711768140b4c7))


### Bug Fixes

* add shutdown hook back to DatabaseConnector.java ([4b2aa61](https://github.com/Panzer1119/codemakers4j/commit/4b2aa61c0afe1ea2e283642de7f00b56966ded9b))
* close inputStream after writeObject in MinIOConnector.java ([9f563ed](https://github.com/Panzer1119/codemakers4j/commit/9f563eda80e78b7a26beda0f44074c5ac80564b7))
* enable initData again in DatabaseConnector.java ([9338fb0](https://github.com/Panzer1119/codemakers4j/commit/9338fb026d0c9cf9ad4133a4da528f8d2c53f7ab))
* implement both writeObject methods in MinIOConnector.java ([6b34c60](https://github.com/Panzer1119/codemakers4j/commit/6b34c609a4902c3872299076214a223d7fd3c93b))
* implement copyObject and moveObject in MinIOConnector.java ([68a39b1](https://github.com/Panzer1119/codemakers4j/commit/68a39b1bdfe4c1044f989bbede2c1586192d8ed9))
* implement removeObject in MinIOConnector.java ([1ed9fbe](https://github.com/Panzer1119/codemakers4j/commit/1ed9fbec65881d3958417abec2ca00d3b45cbef0))
* improve readObject methods in MinIOConnector.java ([3bbe5ad](https://github.com/Panzer1119/cjp/commit/3bbe5ad9f741936a27cc02f8e80f9665896cb540))
* make type generic of readObject function in ObjectStorageConnector.java ([63c1f79](https://github.com/Panzer1119/cjp/commit/63c1f799926bf0908edf309174e398e99f386f6e))
* make type generic of readObject function in ObjectStorageConnector.java ([36cca1c](https://github.com/Panzer1119/cjp/commit/36cca1cac6e507de6fcb2b4d351079e518d1a610))
* remove another HibernateException from JsonType.java ([0ac3f63](https://github.com/Panzer1119/cjp/commit/0ac3f63944b613f7732fd708b540045a944420b0))
* remove code smells from JsonType.java ([eb672be](https://github.com/Panzer1119/cjp/commit/eb672be6f71b74d34ae3e5ae0511f752b730f970))
* revert useSession returning boolean in HibernateUtil.java ([375b3f0](https://github.com/Panzer1119/cjp/commit/375b3f04a85e8c9c921aeef177fd367284c03318))

## [0.1.0](https://github.com/Panzer1119/cjp/compare/v0.16.4...v0.1.0) (2021-08-03)

### [0.16.14](https://github.com/Panzer1119/cjp/compare/v0.16.13...v0.16.14) (2021-08-09)

### [0.16.13](https://github.com/Panzer1119/cjp/compare/v0.16.12...v0.16.13) (2021-08-09)


### Features

* **i18n:** add convenient (un)register methods to I18nReloadEventListener.java ([d5717a1](https://github.com/Panzer1119/cjp/commit/d5717a1c9de6365dfc682bf9e04e4603245c927d))
* **i18n:** add custom ResourceBundle support to I18nUtil.java ([8ddaa5f](https://github.com/Panzer1119/cjp/commit/8ddaa5fe6153396133b7f71041072958272d2d3a))
* **i18n:** add i18n console ResourceBundle ([3ef586d](https://github.com/Panzer1119/cjp/commit/3ef586deb202ff7cbeb45a10323aff303f8c5c53))
* **i18n:** add i18n log_level ResourceBundle ([d9db3ad](https://github.com/Panzer1119/cjp/commit/d9db3ad0d1903962a64b9432571af539a3bc9048))
* **i18n:** add i18n ui ResourceBundle ([f4e4820](https://github.com/Panzer1119/cjp/commit/f4e4820fa772087ea81c1d5c6219d9a043d42762))
* **i18n:** create I18nReloadEvent.java, I18nReloadEventHandler.java and I18nReloadEventListener.java ([3a757e3](https://github.com/Panzer1119/cjp/commit/3a757e39ff988dc2e811b3a8b8a0cb339b0a69e4))
* **i18n:** create I18nUtil.java ([a923028](https://github.com/Panzer1119/cjp/commit/a923028b0f06f4b828f1e27406e32efd802c5bf5))
* **logging:** add default LogEventFormatter without source ([88e8dc7](https://github.com/Panzer1119/cjp/commit/88e8dc7c9a9c8093db654748930c1dbdacbe0219))
* rework EventHandler.java and IEventHandler.java ([5966970](https://github.com/Panzer1119/cjp/commit/59669708f791b6c587432525bad7ae99cdfe7a09))


### Bug Fixes

* **console:** add minimum level to automatically set a bunch of log levels to be shown ([2ff44af](https://github.com/Panzer1119/cjp/commit/2ff44af6e695282b33ea7b828c55d33769f4cac3))
* **console:** load language as the last step in Console.java and DefaultConsole.java ([35f1eb7](https://github.com/Panzer1119/cjp/commit/35f1eb79cb052ef408b34af3b3543887752d7d3d))
* **console:** move localization of Log Levels from LogLevelStyle.java to LogLevel.java ([140044f](https://github.com/Panzer1119/cjp/commit/140044fe69c901ab29392d9783f0c7fd56c727f2))
* **i18n:** add logger to I18nReloadEventHandler.java ([ab93520](https://github.com/Panzer1119/cjp/commit/ab93520103ccc9be555f3b37839598b26dfdbbf9))
* **i18n:** add missing log level keys to i18n log_level ResourceBundle ([270660b](https://github.com/Panzer1119/cjp/commit/270660b6b4e604cc3184612e75480d8b38446519))
* **i18n:** add project.restart to i18n ui ResourceBundle ([f60fd58](https://github.com/Panzer1119/cjp/commit/f60fd58028676b091498fc77eb181f7ef6f4b593))
* **i18n:** add settings tabs to i18n console ResourceBundle ([e8ee800](https://github.com/Panzer1119/cjp/commit/e8ee80037318cf22a456a7d99fbf4807f81820ee))
* **i18n:** add ui key to ui ResourceBundle ([21bef2a](https://github.com/Panzer1119/cjp/commit/21bef2af7fe434ee7b016714fc9b03286931867c))
* **i18n:** disable loading languages in initLocalizers in LanguageUtil.java ([222ac88](https://github.com/Panzer1119/cjp/commit/222ac88dba97eacd9b7c6b8404a0b912de4a8511))
* **i18n:** set force events to true in I18nReloadEventHandler.java ([2ad3044](https://github.com/Panzer1119/cjp/commit/2ad304463627dc969927c6a65a8c8fb53743f4fb))
* **i18n:** use correct parameterization of ConsoleSettings in Console.java and DefaultConsole.java ([7da778b](https://github.com/Panzer1119/cjp/commit/7da778bf4551754f0f81d584cb5fe2a36c517925))
* **logging:** add log level formatting to LogEventFormatter and LogEventFormatterBuilder ([b0cb7e0](https://github.com/Panzer1119/cjp/commit/b0cb7e0e11e0f00f9d626afcbc793904acd74fc0))
* **logging:** encase SourceFormatter in square brackets ([2521948](https://github.com/Panzer1119/cjp/commit/25219480228091dd0745a287ca14bc2a421c1813))
* **logging:** use LogLevelStyle names to determine the MAXIMUM_NAME_LENGTH instead of Level names ([4f211b4](https://github.com/Panzer1119/cjp/commit/4f211b42735d808d4076c11da2c72e5a32aba089))

### [0.16.12](https://github.com/Panzer1119/cjp/compare/v0.16.11...v0.16.12) (2021-08-06)

### [0.16.11](https://github.com/Panzer1119/cjp/compare/v0.16.10...v0.16.11) (2021-08-06)

### [0.16.10](https://github.com/Panzer1119/cjp/compare/v0.16.9...v0.16.10) (2021-08-06)

### [0.16.9](https://github.com/Panzer1119/cjp/compare/v0.16.8...v0.16.9) (2021-08-06)

### [0.16.8](https://github.com/Panzer1119/cjp/compare/v0.16.7...v0.16.8) (2021-08-06)

### [0.16.7](https://github.com/Panzer1119/cjp/compare/v0.16.6...v0.16.7) (2021-08-06)


### Features

* add Console getter to Standard.java ([52a9ed9](https://github.com/Panzer1119/cjp/commit/52a9ed9aff395900b44d840a149dd58e21a5ba57))
* add line separator getter to SystemProperties.java ([bb72b8b](https://github.com/Panzer1119/cjp/commit/bb72b8be5aa0d148c0d6af0f606999ba3b62604a))
* create Formatter.java and Builder.java and edit AbstractFormatBuilder.java ([436149a](https://github.com/Panzer1119/cjp/commit/436149a8bdea2b3e70214ae7bac670b6c1e94dde))
* create JFrameTitleFormatter.java and JFrameTitleFormatterBuilder.java ([1b8cbf4](https://github.com/Panzer1119/cjp/commit/1b8cbf43b4d759aacce3d51c80ec8d89295848d6))
* create log4j2 plugin ConsoleAppender.java ([e350c7a](https://github.com/Panzer1119/cjp/commit/e350c7a9bda3714d30e885ccabe9ea1735cdba4e))
* create LogEventFormatter.java and SourceFormatter.java ([ea4b69c](https://github.com/Panzer1119/cjp/commit/ea4b69cb19ca28219cef56627015c7ed9aaad9f6))
* improve loglevel conversion in GraphicConsoleAppender.java ([3ff210b](https://github.com/Panzer1119/cjp/commit/3ff210b6e517d425480ba5461f75230622ff0ddd))
* rework LogLevel.java, Console.java and DefaultConsole.java and create LogLevelStyle.java ([5e1f739](https://github.com/Panzer1119/cjp/commit/5e1f73936f4162d137674a073f8779fbc80a3076))


### Bug Fixes

* add console reload to GraphicConsoleAppender.java append ([b734402](https://github.com/Panzer1119/cjp/commit/b7344028b26f9eeeb083509f94a3dc4c49e003b0))
* add missing language keys for new LogLevels ([e5e3801](https://github.com/Panzer1119/cjp/commit/e5e38015bf07297489b477edb5538339b01efe78))
* add missing logger variable to base/ ([919fc91](https://github.com/Panzer1119/cjp/commit/919fc917d15f27136b3ff4d7966c9d02f08579ec))
* add missing logger variable to base/action and remove old logger code from CJP.java ([16cfd26](https://github.com/Panzer1119/cjp/commit/16cfd26d995a0429cf133b385f4fa576cf46fc69))
* add missing logger variable to base/util/tough ([0fb60fc](https://github.com/Panzer1119/cjp/commit/0fb60fc8c0b845e3035ab48fb47fd0d524e76d6e))
* add missing logger variable to io/ ([341bce2](https://github.com/Panzer1119/cjp/commit/341bce27adde0d4b4592a272492a22c395d7da83))
* add missing logger variable to lang/ ([4ddc152](https://github.com/Panzer1119/cjp/commit/4ddc1526137d512a4c0559e3a3bf9ffa71721df8))
* add missing logger variable to security/ ([92e3bca](https://github.com/Panzer1119/cjp/commit/92e3bca377152ecc6082bfc773a0a1c745982330))
* add missing logger variable to security/interfaces ([00646d2](https://github.com/Panzer1119/cjp/commit/00646d2538dedea7d2fe97abcad165a1209306ea))
* add missing logger variable to SpeakText.java ([b8cb5f4](https://github.com/Panzer1119/cjp/commit/b8cb5f4f0c0b35ac8aae764b3853a60983be26e5))
* add missing logger variable to util/interfaces and 2 other interfaces ([a64d602](https://github.com/Panzer1119/cjp/commit/a64d602534a68ad3c08961b59315348dcc9f64f8))
* edit Formattable.java ([5cc4bcd](https://github.com/Panzer1119/cjp/commit/5cc4bcd662de969f7b154256958e1f52573b176c))
* implement append for testing purposes in GraphicConsoleAppender.java ([0fdcbfc](https://github.com/Panzer1119/cjp/commit/0fdcbfc59afa3b748d5786c27a2faf8504cccad6))
* remove LogLevel OFF and ALL from LogLevel.LEVELS ([210c546](https://github.com/Panzer1119/cjp/commit/210c546b06865b8578d3fb62aadfefdaa17dd166))
* remove old Logger call from WindowsSystemInfo.java ([afd82d8](https://github.com/Panzer1119/cjp/commit/afd82d898c7c02f66e57554c59a327903152f556))
* use consoleName instead of console in GraphicConsoleAppender.Builder ([7565bed](https://github.com/Panzer1119/cjp/commit/7565bed22aa54201006f245200fc77b82df1fee9))
* use correct class name in toString method in LogEventFormatterBuilder.java and SourceFormatterBuilder.java ([1c5a6b0](https://github.com/Panzer1119/cjp/commit/1c5a6b02f1385d81e595ce2b1129b2d6683a9215))
* use formatted message in append in GraphicConsoleAppender.java ([24c21b6](https://github.com/Panzer1119/cjp/commit/24c21b656abbcfee72d2ea3f0b159fd4b4661f2a))
* use loggerTODO temporary as variable name logger is already in use in Console.java and DefaultConsole.java ([c13a947](https://github.com/Panzer1119/cjp/commit/c13a94782469270d48c9f49244eac2d6a22a53b5))
* use loglevel input for console input in GraphicConsoleAppenderTest.java ([58523c1](https://github.com/Panzer1119/cjp/commit/58523c11d4af631b326ba5e97821f96db099edd1))
* use PluginBuilderFactory instead of PluginFactory Annotation in GraphicConsoleAppender.java ([35607ed](https://github.com/Panzer1119/cjp/commit/35607edbfd824d791103e90b09f24ac272f1aa58))

### [0.16.6](https://github.com/Panzer1119/cjp/compare/v0.16.5...v0.16.6) (2021-08-04)


### Features

* rework ByteSerializable.java ([1b295a5](https://github.com/Panzer1119/cjp/commit/1b295a53db7126853526806848fa391b3cbe8e4e))
* rework JOSFileList.java and JOSFileSet.java ([7acb002](https://github.com/Panzer1119/cjp/commit/7acb002b937a4b832dc90e7916c59e02cae2407d))
* rework KeepingIncrementalData.java ([ae70d9f](https://github.com/Panzer1119/cjp/commit/ae70d9f4fe38e913d108e3afb67eb8d8ebd29a23))
* rework Result.java and ReturningResult.java ([daff380](https://github.com/Panzer1119/cjp/commit/daff3808090c8982804e5f55b6a62efc3c8b59bc))
* rework SerializationUtil.java ([8969f18](https://github.com/Panzer1119/cjp/commit/8969f18dd645dd5f41373dcd0423148c91230d57))


### Bug Fixes

* fix base/entities/data ([5ea835e](https://github.com/Panzer1119/cjp/commit/5ea835e36a3cfe0218c163abdce341f1564605ea))
* fix ComplexDouble.java ([f99bd02](https://github.com/Panzer1119/cjp/commit/f99bd02194a32b08d133fe246282087e366b21c4))
* fix IncrementalObject.java ([dbdf190](https://github.com/Panzer1119/cjp/commit/dbdf190c359af4f6ebe695f64e1ee9b3f0e41346))
* fix io/streams ([2233312](https://github.com/Panzer1119/cjp/commit/22333124b203b126f9599cb1405330b2bae7fa92))
* fix Result.java and ReturningResult.java ([f1dca2e](https://github.com/Panzer1119/cjp/commit/f1dca2ecaa3287b0f68560da7014e45351fd5e2b))

### [0.16.5](https://github.com/Panzer1119/cjp/compare/v0.16.4...v0.16.5) (2021-08-03)


### Features

* add getResourceAsStream methods to IOUtil.java ([042a8ad](https://github.com/Panzer1119/cjp/commit/042a8addbcf33fe910d325c3c750bc78c49dd7d4))

### [0.16.4](https://github.com/Panzer1119/cjp/compare/v0.16.3...v0.16.4) (2021-08-03)


### Features

* create UUIDUtil.java ([4f5f3ba](https://github.com/Panzer1119/cjp/commit/4f5f3bae2e32af6caf9a95eeec5b5e52f2a804f1))

### [0.16.3](https://github.com/Panzer1119/cjp/compare/v0.16.2...v0.16.3) (2021-08-03)


### Features

* create FileUtil.java ([58f34c3](https://github.com/Panzer1119/cjp/commit/58f34c31fcc62707dd95c9bdc52d0fde4278bcbb))

### [0.16.2](https://github.com/Panzer1119/cjp/compare/v0.16.1...v0.16.2) (2021-08-03)


### Features

* create ToughTriConsumer.java ([2754dc5](https://github.com/Panzer1119/cjp/commit/2754dc581401ce882657d56c721724e415eb048a))

### [0.16.1](https://github.com/Panzer1119/cjp/compare/v0.16.0...v0.16.1) (2021-08-03)

## 0.16.0 (2021-08-03)


### Bug Fixes

* replace ClassFormatException with ClassCastException in Require.java ([45d1101](https://github.com/Panzer1119/cjp/commit/45d1101becef998ba5e64231dd3ccc9c97332845))
