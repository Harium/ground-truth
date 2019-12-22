# propan-gdx-template
A ready-to-go empty project

## Description
If you are new to [Propan](http://github.com/Harium/propan) or have no time/patience to configure a new project over and over, clone or fork this repository.

This project includes lwjgl backend (via maven) and libgdx libraries (via jar).

You can import this project to your favorite IDE just pointing to the pom.xml file when importing.

Do not forget to include [libgdx jars](https://libgdx.badlogicgames.com/ci/nightlies/dist) to your project.

## Downloading Native Libraries
- [LibGDX Natives](https://libgdx.badlogicgames.com/ci/nightlies/dist/gdx-natives.jar)
- [LibGDX Freetype Natives](https://libgdx.badlogicgames.com/ci/nightlies/dist/gdx-natives.jar)

Command line:
```
wget https://libgdx.badlogicgames.com/ci/nightlies/dist/gdx-natives.jar
wget https://libgdx.badlogicgames.com/ci/nightlies/dist/extensions/gdx-freetype/gdx-freetype-natives.jar
```

## Including to the project

### IntelliJ
File -> Project Structure (Ctrl+Shift+Alt+S)-> Libraries -> Add (plus sign) -> Java -> path to native jar
Note that you have to include one jar each time.

## This is a free project (Public Domain)
Feel free to modify your project as much as you want.
