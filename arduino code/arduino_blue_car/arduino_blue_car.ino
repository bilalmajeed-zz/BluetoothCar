
// bluetooth_basics.ino

int moveUpPin = 3;
int moveDownPin = 4;
int moveRightPin = 5;
int moveLeftPin = 6;
int state = 0;
int flag = 0;

void setup() {

pinMode(moveUpPin, OUTPUT);
pinMode(moveDownPin, OUTPUT);
pinMode(moveRightPin, OUTPUT);
pinMode(moveLeftPin, OUTPUT);

digitalWrite(moveUpPin, LOW);
digitalWrite(moveDownPin, LOW);
digitalWrite(moveRightPin, LOW);
digitalWrite(moveLeftPin, LOW);
Serial.begin(9600); //connection rate for the BT module

Serial.println("enter a key");
}

void loop() {
    while (Serial.available()==0) {
    }

    state = Serial.read();
    flag = 0;

    if (state == '0') {
        digitalWrite(moveUpPin, LOW);
        digitalWrite(moveDownPin, LOW);
        digitalWrite(moveRightPin, LOW);
        digitalWrite(moveLeftPin, LOW);
        if (flag == 0) {
            Serial.println("Off");
            flag = 1;
        }
    }

    if (state == '1') {
        digitalWrite(moveUpPin, HIGH);
        digitalWrite(moveDownPin, LOW);
        digitalWrite(moveRightPin, LOW);
        digitalWrite(moveLeftPin, LOW);
        if (flag == 0) {
            Serial.println("Move Up");
            flag = 1;
        }
    }

    if (state == '2') {
        digitalWrite(moveUpPin, LOW);
        digitalWrite(moveDownPin, HIGH);
        digitalWrite(moveRightPin, LOW);
        digitalWrite(moveLeftPin, LOW);
        if (flag == 0) {
            Serial.println("Move Down");
            flag = 1;
        }
    }

    if (state == '3') {
        digitalWrite(moveUpPin, LOW);
        digitalWrite(moveDownPin, LOW);
        digitalWrite(moveRightPin, HIGH);
        digitalWrite(moveLeftPin, LOW);
        if (flag == 0) {
            Serial.println("Move Right");
            flag = 1;
        }
    }

    if (state == '4') {
        digitalWrite(moveUpPin, LOW);
        digitalWrite(moveDownPin, LOW);
        digitalWrite(moveRightPin, LOW);
        digitalWrite(moveLeftPin, HIGH);
        if (flag == 0) {
            Serial.println("Move Left");
            flag = 1;
        }
    }
}


