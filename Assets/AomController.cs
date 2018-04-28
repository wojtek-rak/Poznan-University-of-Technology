using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AomController : MonoBehaviour {


    //public Vector2 atomPosition;
    public Rigidbody2D rb;
	// Use this for initialization
	void Start () 
    {
        float angle = Random.Range(30f, 150f) + Random.Range(0, 2) * 180f;
        float radAngle = angle * Mathf.Deg2Rad;
        Vector2 dir = new Vector2(Mathf.Cos(radAngle), Mathf.Sin(radAngle));
        float xSpin = Random.Range(0, 360);
        float ySpin = Random.Range(0, 360);
        //Debug.Log(xSpin);
        rb = gameObject.GetComponent<Rigidbody2D>();
        //rb.velocity = new Vector2(5, 0);
        rb.velocity = dir * 4f;
        //rb.transform.Rotate(new Vector3(0f, 0f, xSpin));

	}
	
    public Vector3 getAtomPosition(GameObject atom)
    {
        return atom.transform.position;
    }


	// Update is called once per frame
	void Update () 
    {
		
	}
}
