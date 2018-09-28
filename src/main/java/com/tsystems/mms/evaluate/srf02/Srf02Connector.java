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
	private static final byte byteCount = 0x01;
	private byte address = 0x00;
	private byte register = 0x00;

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
		//byte respond = writeRegister(address, register, (byte) 0x50D);
		wait(timeout);
		byte result = readRegister(address, register);
		return null;
	}
	
	@Override
	public double getDistance() throws IOException, InterruptedException {
        byte respond = writeRegister(address, register, (byte) 0x54);
        wait(timeout);
        byte result = readRegister(address, register);
        if (respond != result){
            return result;
        }
        else {
            throw new IOException("Device doesn't respond ");
        }
	}
	
	// ================================================================================================================
	
	private byte readRegister(byte address, byte register) throws IOException {
		OutputStream os;
		InputStream is;
		os = port.getOutputStream();
		is = port.getInputStream();
		
		byte[] cmd = {I2C_AD1, address, register};	// TODO
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

	public byte testCommunication() throws IOException {
		OutputStream os;
		InputStream is;
		os = port.getOutputStream();
		is = port.getInputStream();

		byte[] cmd = {0x5A, 0x01, 0x00, 0x00};	// TODO
		for (int i = 0; i<cmd.length; i++){
			System.out.println(cmd[i]);
		}
		os.write(cmd);
		os.flush();

		// READ RESULT (in case of write command, too!)
		int result = is.read();
		System.out.println(result);

		is.close();
		os.close();

		return (byte)(result & 0x00FF);
		//return 0x00;
	}
	
	void close() {
		if(this.port!=null) {
			this.port.close();
		}
	}

}
