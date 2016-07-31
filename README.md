# Bluetooth Car

Bluetooth controlled RC Car (using Arduino, Android, and BT)

---

###Overview
Utilizing a serial transmitting Bluetooth module hooked up to an Arduino board paired with a few DC motors and voila -- A Bluetooth Controlled R/C Car. This project also has a android app that does the actual controlling of the car, sending directions signals and receiving feedback over Bluetooth.

###How it Works
The android app consists of four buttons, one for each direction, and each button on touch sends a unique signal to the Arduino board via Bluetooth which then goes on to process the input. The Arduino then proceeds to send out signals to a motor controller depending on the output which in turn make the wheel in the car go round and round.

![alt text](https://raw.githubusercontent.com/bilalmajeed/BluetoothCar/master/images/front.jpg "R/C Car Exterior")
![alt text](https://raw.githubusercontent.com/bilalmajeed/BluetoothCar/master/images/ciruit.jpg "R/C Car Circuitry")
![alt text](https://raw.githubusercontent.com/bilalmajeed/BluetoothCar/master/images/internals.jpg "R/C Car Interior")


[Demo Video](https://youtu.be/Yl8o7LEbfMI)
