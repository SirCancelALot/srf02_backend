package com.tsystems.mms.evaluate.srf02;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Srf02Connector implements DistanceMeasurementProvider
{
	private static final int timeout = 100;
	private static final int BAUD = 19200;
	private static final byte I2C_AD1 = 0x55;
	private static final byte I2C_USB = 0x5A;
	private static final byte byteCount = 0x01;
	private static final byte commandRegister = 0x00;
	private byte address = 0x00;

	private SerialPort port;
	
	public Srf02Connector(String comPort) throws Exception {
		try {
			CommPortIdentifier commPortIdentifier =
				CommPortIdentifier.getPortIdentifier(comPort);
			CommPort comport = commPortIdentifier.open(Srf02Connector.class.getSimpleName(), timeout);
			if( comport instanceof SerialPort ) {
				((SerialPort) comport).setSerialPortParams(BAUD, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
				this.port = (SerialPort) comport;
			}
		} catch (Exception e) {
			throw new Exception("Cannot initialize SRF02 Connector: " + (e.getClass().getName()) + " :: " + e.getMessage());
		}
	}


	
	public String getVersion() throws IOException, InterruptedException {
		return null;
	}
	
	@Override
	public double getDistance() throws IOException, InterruptedException {

        return -1;
	}
	
	// ================================================================================================================

	private byte readRegister(byte address, byte register) throws IOException {
		OutputStream os;
		InputStream is;
		os = port.getOutputStream();
		is = port.getInputStream();
		
		byte[] cmd = {I2C_AD1, address, register, byteCount};	// TODO
		os.write(cmd);
		os.flush();
		
		// READ RESULT
		int result = is.read();
		
		is.close();
		os.close();
		
		return (byte)(result & 0x00FF);
	}
	
	private byte writeRegister(byte address, byte register, byte data) throws IOException {
		OutputStream os;
		InputStream is;
		os = port.getOutputStream();
		is = port.getInputStream();
		
		byte[] cmd = {I2C_AD1, address, register, byteCount, data};	// TODO
		os.write(cmd);
		os.flush();
		
		// READ RESULT (in case of write command, too!)
		int result = is.read();
		
		is.close();
		os.close();
		
		return (byte)(result & 0x00FF);
	}
	
	void close() {
		if(this.port!=null) {
			this.port.close();
		}
	}

	public byte testmethod() throws Exception {
		OutputStream os;
		InputStream is;
		int result = -2;

		for(int i = 0xE0; i<0xFF; i = i + 2){

			os = port.getOutputStream();
			is = port.getInputStream();


			byte[] cmd = {0x55, (byte) (i+1), 0x00, 0x01};	// TODO
			os.write(cmd);
			os.flush();

			// READ RESULT
			result = is.read();
			System.out.println(result);

			is.close();
			os.close();
		}


		return (byte)(result & 0x00FF);
	}

	public void findAndSetAddress() throws Exception {
		for (int i = 0xE0; i<0xFF; i = i + 2){
			byte result = readRegister((byte) (i+1), commandRegister);
			if (result != -1){
				address = (byte) i;
				System.out.println(address);
				return;
			}
		}
		throw new Exception("Couldn't find Srf02-Address");
	}

	public byte testUSBVersion() throws IOException {
		OutputStream os;
		InputStream is;
		os = port.getOutputStream();
		is = port.getInputStream();

		byte[] cmd = {I2C_USB, 0x01, 0x00, 0x00};	// TODO
		os.write(cmd);
		os.flush();

		// READ RESULT (in case of write command, too!)
		int result = is.read();

		is.close();
		os.close();
		System.out.println((byte)(result & 0x00FF));

		return (byte)(result & 0x00FF);
	}
}
