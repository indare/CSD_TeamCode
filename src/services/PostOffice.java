package services;

import java.util.Vector;

public class PostOffice {
	private static PostOffice instance;

	Vector<String> _log;
	
	private PostOffice() {
		_log = new Vector<String>();
	}

	public static PostOffice getInstance() {
		if (instance == null) {
			instance = new PostOffice();
		}
		return instance;
	}
	
	public int size() {
		return _log.size();
	}

	public void sendEMail( String address, String message ) {
		String logString = String.format( "<sendEMail address=\"%s\" >%s</sendEmail>\n", address, message );
		_log.add( logString );
	}
	
	public String findEmail( String to, String messageContains ) {
		String ret = "";
		String log = "";
		for( int i = 0; i < _log.size(); i++ ) {
			log = _log.get( i );
			if( log.contains( String.format( "address=\"%s\"", to ) )) {
				if( log.contains( messageContains ))
					ret += log;
				}
			}
		return ret;
	}

	public boolean doesLogContain(String to, String message) {
		boolean ret = false;
		String line = "";
		for( int i = 0; i < _log.size(); i++ ) {
			line = _log.get( i );
			if( line.contains( to )) {
				if( line.contains( message ))
					ret = true;
				}
			}
		return ret;

	}

	public void clear() {
		_log.clear();
	}
}
