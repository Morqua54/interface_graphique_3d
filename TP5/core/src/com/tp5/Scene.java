package com.tp5;

import com.badlogic.gdx.math.Vector3;

public class Scene {
    public Sphere3D sphere;

    Scene()
    {
        sphere = new Sphere3D("sphere", 1, 0, 0, 0, new Vector3 (0,1,10), 4, new Vector3(0,1,1));
    }
}
