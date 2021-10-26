package com.tp5;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class Scene {
    private Sphere3D sphere;

    Scene()
    {
        sphere = new Sphere3D("sphere", 1, 0, 0, 0, new Vector3 (0,5,15), 4, new Vector3(1,1,1));
    }
}
