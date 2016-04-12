/*
  Copyright 2016 Carter Turnbaugh

  This file is part of Terca Java Sockets.

  Terca Java Sockets is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  Terca Java Sockets is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with Terca Java Sockets.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.tercatech.jtsocket;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;

public class JTClientSecure extends JTClient implements SocketSecureIntf {
	protected final JTSocketSecure secure;

	public JTClientSecure(String address, int port, String key) throws NoSuchProviderException{
		super(address, port);

		try{
			secure = new JTSocketSecure(key);
		}
		catch(NoSuchProviderException e){
			System.out.println("BouncyCastle Cryptography probably not installed");
			throw e;
		}
	}

	public JTClientSecure(String key) throws NoSuchProviderException{
		super();
		try{
			secure = new JTSocketSecure(key);
		}
		catch(NoSuchProviderException e){
			System.out.println("BouncyCastle Cryptography probably not installed");
			throw e;
		}
	}

	public String sRead() throws IOException, InvalidKeyException{
		return secure.decrypt(super.cRead());
	}

	public void sWrite(String data) throws IOException, InvalidKeyException{
		super.cWrite(secure.encrypt(data));
	}
}
