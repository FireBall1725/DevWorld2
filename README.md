# DevWorld 2

![](https://img.shields.io/circleci/build/github/FireBall1725/DevWorld2/master?label=Build&logo=CircleCI&style=for-the-badge)
![](https://img.shields.io/codeclimate/maintainability/FireBall1725/DevWorld2?logo=Code%20Climate&style=for-the-badge)
![](https://img.shields.io/codeclimate/issues/FireBall1725/DevWorld2?logo=Code%20Climate&style=for-the-badge)
![https://discord.gg/fireball1725](https://img.shields.io/discord/149023060199079936?logo=Discord&logoColor=%23ffffff&style=for-the-badge)
![](https://img.shields.io/badge/dynamic/json?logo=GitHub&color=brightgreen&label=Lines&query=lines&url=https%3A%2F%2Ftokei.rs%2Fb1%2Fgithub%2FFireBall1725%2Fdevworld&style=for-the-badge)

DevWorld is a Minecraft mod that adds a 1-click development world. This world by default is set to use the Redstone Ready flat world generator with time fixed at noon, weather disabled, and a few other things.

![](https://media.forgecdn.net/attachments/266/343/screen-shot-2019-10-19-at-14.png)

## Installation

Like all Minecraft mods, this mod can be dropped into the mods folder, however it was designed to be a dependency in your gradle script.

```groovy
repositories {
  maven {
    name "FireBall1725 maven"
    url "https://repo.erins.net/maven"
  }
}

dependencies {
  runtimeOnly fg.deobf(group: 'ca.fireball1725.mods', name: 'DevWorld2-1.14.4', version: '1.0+')
}
```

## Configuration

At this time this mod has no configuration

## License

Mod source code is licensed under the [GNU AFFERO GENERAL PUBLIC LICENSE](https://www.gnu.org/licenses/agpl-3.0.en.html). Click the link for more information

Mod assets are licensed under the [Attribution-NonCommercial-NoDerivatives 4.0 International (CC BY-NC-ND 4.0)](https://creativecommons.org/licenses/by-nc-nd/4.0/). Click the link for more information

