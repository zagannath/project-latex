/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project.latex.sensor;

/**
 *
 * @author dgorst
 */
public interface GPSSensorController extends SensorController {
    float getLatitude();
    
    float getLongitude();
}
