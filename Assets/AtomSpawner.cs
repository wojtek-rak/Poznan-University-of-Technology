using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AtomSpawner : MonoBehaviour {

    public float atoms;
    public int num_attoms; // (9 / numofatoms )^2
    public GameObject atomCreate;

	// Use this for initialization
	void Start () {
        float sum_i;
        float sum_j;
        for (float j = 1; j <= num_attoms * 2 ; j += 2)
        { 
            for (float i = 1; i <= num_attoms; i += 1)
            {
                sum_i = i / num_attoms * 1f;
                sum_j = j / num_attoms * 4f;
                Instantiate(atomCreate, new Vector3(sum_i, sum_j, 0f), Quaternion.identity);
            }

        }

        
	}
	
	// Update is called once per frame
	void Update () {
        //foreach in atom
	}
}
