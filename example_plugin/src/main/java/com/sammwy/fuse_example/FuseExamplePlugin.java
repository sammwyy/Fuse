package com.sammwy.fuse_example;

import fuse.plugins.Plugin;

public class FuseExamplePlugin extends Plugin {
    @Override
    public void onStart() {
        System.out.println("Hello from FuseExamplePlugin!");
    }
}
