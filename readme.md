**Sample Bot**

This is project written on Kotlin language that can be use for developing your own telegram bot.
You can copy this project, fork or import to another repository.
There are several steps to take for start develop your bot.

+ *Create configuration file*

    You should create new file in project root directory and name it as "config.json".
    This file will use bot as configurations and will take some necessary data for bot working.
    There is one data that you must provide it is "botKey". As you can see "config.json" have JSON format
    and base view of this file must be like it:
    ```
        {
            "botKey": "token of your bot"
        }
    ```
  This token you can get [here](https://t.me/BotFather)
+ *Change project name*
    
    Find file "settings.gradle.kts" in project and change **rootProject.name**
    to your own name.
+ *Change project group*
  
    Find file "build.gradle.kts" in project and change **group** to your own group.


After completed this steps you can start bot development! Enjoy coding!