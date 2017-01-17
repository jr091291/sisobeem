package sisobeem.artifacts.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sun.jna.platform.unix.X11;

import sisobeem.artifacts.Log;

import static sisobeem.artifacts.Log.getLog;
public class LogTest {
	Log x;
	public LogTest() {
		x=getLog();
	}
	@Test
	public void test() {
		x.setWarn("prueba warn");
		
	}

}
