package com.tp5;
import com.badlogic.gdx.math.Vector3;

public class Light {
    public Vector3 position;
    public String name;

    Light(String name, Vector3 position)
    {
        this.position = position;
        this.name = name;
    }
}
