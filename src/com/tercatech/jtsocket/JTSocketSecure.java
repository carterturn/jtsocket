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

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.NoSuchProviderException;

public class JTSocketSecure {
	protected final Cipher aes;
	private final IvParameterSpec iv;
	private final SecretKeySpec key;

	public JTSocketSecure(String key) throws NoSuchProviderException{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		this.key = new SecretKeySpec(key.getBytes(), "AES");
		this.iv = new IvParameterSpec(key.getBytes());

		Cipher temp = null;
		try{
			temp = Cipher.getInstance("AES/CBC/NoPadding", BouncyCastleProvider.PROVIDER_NAME);
		}
		catch(NoSuchAlgorithmException e){
			System.out.println("AES not installed (this should never happen");
			e.printStackTrace();
		}
		catch(NoSuchPaddingException e){
			System.out.println("NoPadding not installed (this is pretty ridiculous");
			e.printStackTrace();
		}
		aes = temp; // Seriously, aes was going to be initialized in the one line in the try?
	}

	public String encrypt(String data) throws InvalidKeyException{
		String result;
		try{
			aes.init(Cipher.ENCRYPT_MODE, key, iv);
			
			int padSize = ((data.length() / 16) + 1)*16 - data.length();
			StringBuilder padBuilder = new StringBuilder(data);
			for(int i = 0; i < padSize; i++) padBuilder.append('\4');

			byte[] ciphertext = aes.doFinal(padBuilder.toString().getBytes());
			StringBuilder resultBuilder = new StringBuilder(data.length());
			for(byte b : ciphertext){
				resultBuilder.append((char) b);
			}
			
			result = resultBuilder.toString();
		}
		catch(InvalidAlgorithmParameterException e){
			e.printStackTrace();
			result = "AES parameters not valid (seriously, why do I need to catch this)";
		}
		catch(IllegalBlockSizeException e){
			e.printStackTrace();
			result = "AES block size not valid (I am literally doing the padding in the try)";
		}
		catch(BadPaddingException e){
			e.printStackTrace();
			result = "Because we could not just have one error about data size";
		}
		
		return result;
	}

	public String decrypt(String data) throws InvalidKeyException{
		String result;
		try{
			aes.init(Cipher.DECRYPT_MODE, key, iv);

			byte[] ciphertext = new byte[data.length()];

			for(int i = 0; i < data.length(); i++){
				ciphertext[i] = (byte) data.charAt(i);
			}

			result = new String(aes.doFinal(ciphertext));
			result = result.substring(0, result.indexOf('\4'));
		}
		catch(InvalidAlgorithmParameterException e){
			e.printStackTrace();
			result = "AES parameters not valid (seriously, why do I need to catch this)";
		}
		catch(IllegalBlockSizeException e){
			e.printStackTrace();
			result = "AES block size not valid (yet somehow it encrypted perfectly)";
		}
		catch(BadPaddingException e){
			e.printStackTrace();
			result = "Because we could not just have one error about data size";
		}
				
		return result;
	}
}
