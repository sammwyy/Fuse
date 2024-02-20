# Example Plugin

This is an example plugin for Fuse. It demonstrates how to create a simple plugin for Fuse.

## MetaData

Metadata file are used to provide information about the plugin. It is a TOML file with the following fields:

| Field | Type | Description | Required |
| --- | --- | --- | --- |
| name | String | The name of the plugin. | ✔ |
| version | String | The version of the plugin. | ✔ |
| main | String | The main class of the plugin. | ✔ |
| description | String | A brief description of the plugin. | ❌ |
| authors | Array of String | The authors of the plugin. | ❌ |
| dependencies | Array of String | The dependencies of the plugin. | ❌ |
| soft_dependencies | Array of String | The soft dependencies of the plugin. | ❌ |
| website | String | The website of the plugin. | ❌ |

## Example plugin

The example plugin is a simple plugin that prints "Hello, world!" when the server starts.

```java
package com.example.plugin;

import fuse.plugins.Plugin;

public class ExamplePlugin extends Plugin {
    @Override
    public void enable() {
        System.out.println("Hello, world!");
    }
}
```

## Register events

You can register events using the `registerEvent` method. The following example registers a `PlayerJoinEvent`:

```java
package com.example.plugin;

import fuse.events.player.PlayerJoinEvent;
import fuse.plugins.Plugin;

public class ExamplePlugin extends Plugin {
    @Override
    public void enable() {
        this.getEventListener().on(PlayerJoinEvent.class, event -> {
            System.out.println(event.getPlayer().getUsername() + " joined the server!");
        });
    }
}
```

## Register Commands

You can register commands using the `registerCommand` method. The following example registers a simple command:

```java
package com.example.plugin;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;

import fuse.plugins.Plugin;

@Command(name = "hello")
class HelloCommand {
    @Execute
    public void execute(@Context Player player) {
        player.sendMessage("Hello, world!");
    }
}

public class ExamplePlugin extends Plugin {
    @Override
    public void enable() {
        this.registerCommand(new HelloCommand());
    }
}
```
