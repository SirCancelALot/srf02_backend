package com.tsystems.mms.evaluate.srf02;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class Srf02Connector implements DistanceMeasurementProvider
{
	private static final int timeout = 100;
	private static final int BAUD = 19200;
	
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
		// TODO
		return null;
	}
	
	@Override
	public double getDistance() throws IOException, InterruptedException {
		// TODO
		return (double) -1;
	}
	
	// ================================================================================================================
	
	private byte readRegister(byte address, byte register) throws IOException {
		OutputStream os;
		InputStream is;
		os = port.getOutputStream();
		is = port.getInputStream();
		
		byte[] cmd = {};	// TODO
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
		
		byte[] cmd = {};	// TODO
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

}
