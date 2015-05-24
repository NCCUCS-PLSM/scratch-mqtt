#Usage

* Step1: Scratch Extension
	1. Launch your Scratch Offline Editor.
	2. Click `File` with pressing shift, there will be an additional option `import experimental HTTP extension`.

	![](image/scratch1.png)
	3. Choose `S4A.s2e` and import it, then you will see some new blocks in `More Blocks`.
	![](image/scratch2.png)

* Step2: MQTT broker

	Launch an MQTT broker.
	
	![](image/mqtt_broker.png)

* Step3: scratch-mqtt Project
	1. Run `ScratchPublisher.java` to listen for Scratch signals and tranfer them to `Subscriber.java`. 	
	2. Run `Subscriber.java` to listen for tranferred data from `ScratchPublisher.java`.

	![](image/subscriber.png)

* Step4: When click blocks on Scrach Editor to run. The result of receiving signal from Scratch Editor will show on ScratchPublisher.
* 
	![](image/scratch_publisher.png)
	
* [Demo](https://www.youtube.com/watch?v=LS80cD2ICdU)
