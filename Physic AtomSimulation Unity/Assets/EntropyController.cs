using System.Collections;
using System.Collections.Generic;
using UnityEngine; 
using System.IO;

public class EntropyController : MonoBehaviour
{


    private GameObject[] atoms;
    private int max_pole_x = 10;
    //private int max_pole_y = 10;
    //private int[][] atomCounter = new int ;
    private int[][] atomCounter = new int[33][];
    private int atomSummer = 0;
    private int stan = 33;
    private float entropy;
    private double stanSummer = 1000000d;
    // Use this for initialization
    void Start()
    {


        for (var i = 0; i < atomCounter.Length; i += 1)
        {
            atomCounter[i] = new int[stan];
        }
            
        for (int i = 0; i < 17; i += 1)
        {
            for (int j = 0; j < stan; j += 1)
            {
                atomCounter[i][j] = 0;
            }
        }
    }

    float Siln(int x)
    {
        int sum = 1;
        for (int i = 1; i <= x; i += 1)
        {
            sum *= i;
        }
        return sum;
    }

    void Update()
    {
        atoms = GameObject.FindGameObjectsWithTag("własny");

        foreach (GameObject atom in atoms)
        {
            atomCounter[(int)(atom.transform.position.x * 4)][(int)(atom.transform.position.y * 4)] += 1;
            atomSummer += 1;
            //if ((int)(atom.transform.position.x * 4) > max_pole_x) max_pole_x = (int)(atom.transform.position.x * 4);
            //if ((int)(atom.transform.position.y * 2) < max_pole_y) max_pole_y = (int)(atom.transform.position.y * 2);
            //Debug.Log((int)(atom.transform.position.x * 2));
            //Debug.Log((int)(atom.transform.position.y * 2));
        }

        //Debug.Log(atomSummer);

        for (int i = 0; i < stan; i += 1)
        {
            for (int j = 0; j < stan; j += 1)
            {
                //Debug.Log(atomCounter[i][j]);
                //Debug.Log(Siln(atomCounter[i][j]));
                stanSummer /= Siln(atomCounter[i][j]);
                atomCounter[i][j] = 0;
            }
        }
        string path = "Assets/Resources/atom_demo.txt";

        //Write some text to the test.txt file
        if (Time.time < 5f)
        {
            StreamWriter writer = new StreamWriter(path, true);
            writer.WriteLine(stanSummer);
            writer.Close();
        }

        //Debug.Log(stanSummer);
        atomSummer = 0;
        stanSummer = 1000000d;
        //Debug.Log(max_pole_x);
        //Debug.Log(max_pole_y);

    }
}