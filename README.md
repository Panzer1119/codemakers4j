# cjp

[![Publish Package to GitHub and create a Release](https://github.com/Panzer1119/cjp/actions/workflows/publish.yml/badge.svg)](https://github.com/Panzer1119/cjp/actions/workflows/publish.yml)

[![Quality Gate Status](https://sonarqube.codemakers.de/api/project_badges/measure?project=de.codemakers%3Acjp&metric=alert_status)](https://sonarqube.codemakers.de/dashboard?id=de.codemakers%3Acjp)

[![Bugs](https://sonarqube.codemakers.de/api/project_badges/measure?project=de.codemakers%3Acjp&metric=bugs)](https://sonarqube.codemakers.de/dashboard?id=de.codemakers%3Acjp)

[![Vulnerabilities](https://sonarqube.codemakers.de/api/project_badges/measure?project=de.codemakers%3Acjp&metric=vulnerabilities)](https://sonarqube.codemakers.de/dashboard?id=de.codemakers%3Acjp)

[![Code Smells](https://sonarqube.codemakers.de/api/project_badges/measure?project=de.codemakers%3Acjp&metric=code_smells)](https://sonarqube.codemakers.de/dashboard?id=de.codemakers%3Acjp)

[![Duplicated Lines (%)](https://sonarqube.codemakers.de/api/project_badges/measure?project=de.codemakers%3Acjp&metric=duplicated_lines_density)](https://sonarqube.codemakers.de/dashboard?id=de.codemakers%3Acjp)

[![Lines of Code](https://sonarqube.codemakers.de/api/project_badges/measure?project=de.codemakers%3Acjp&metric=ncloc)](https://sonarqube.codemakers.de/dashboard?id=de.codemakers%3Acjp)

CJP Library (Codemakers Java Plus)

## Developer Guide

Make sure you have set up your local Git Hooks:

```sh
git config core.hooksPath .githooks
```

This will make sure your commit messages follow the [Conventional Commits Specification](https://www.conventionalcommits.org/en/v1.0.0/).

Here's some handy commands:

| Command | Usage |
|---------|-------|
| `gradle test` | Run the tests. |
| `gradle build` | Run the builds. |
| `gradle shadowJar` | Create the Uber Jar with all Dependencies. |

## Semantic Versioning

This project uses `standard-release` to update the version in the `build.gradle` file from the changes in the history and to create the `CHANGELOG.md` file.

Any time you want to cut a new release, run:

```sh
npx dwmkerr/standard-version --sign
```

This will:

- Update the `CHANGELOG.md`
- Update the version number based on the commit history
- Create a git tag with the new version number

Finally, just push the tag to trigger a deployment of the new version:

```sh
git push --follow-tags
```
