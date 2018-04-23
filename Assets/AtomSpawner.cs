using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AtomSpawner : MonoBehaviour {

    public float atoms;
    public GameObject atom;

	// Use this for initialization
	void Start () {
        float sum_i;
        float sum_j;
        for (int j = -4; j <= 4 ; j += 1)
        { 
            for (int i = -4; i <= 4; i += 1)
            {
                sum_i = j / atoms * 3.5f;
                sum_j = i / atoms * 3.5f;
                Instantiate(atom, new Vector3(sum_i, sum_j, 0f), Quaternion.identity);
            }

        }

        
	}
	
	// Update is called once per frame
	void Update () {
        //foreach in atom
	}
}
