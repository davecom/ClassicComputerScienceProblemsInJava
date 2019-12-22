// UnbreakableEncryption.java
// From Classic Computer Science Problems in Java Chapter 1
// Copyright 2020 David Kopec
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package chapter1;

import java.util.Random;

public class UnbreakableEncryption {
	// Generate *length* random bytes
	private static byte[] randomKey(int length) {
		byte[] dummy = new byte[length];
		Random random = new Random();
		random.nextBytes(dummy);
		return dummy;
	}

	public static KeyPair encrypt(String original) {
		byte[] originalBytes = original.getBytes();
		byte[] dummyKey = randomKey(originalBytes.length);
		byte[] encryptedKey = new byte[originalBytes.length];
		for (int i = 0; i < originalBytes.length; i++) {
			// XOR every byte
			encryptedKey[i] = (byte) (originalBytes[i] ^ dummyKey[i]);
		}
		return new KeyPair(dummyKey, encryptedKey);
	}

	public static String decrypt(KeyPair kp) {
		byte[] decrypted = new byte[kp.key1.length];
		for (int i = 0; i < kp.key1.length; i++) {
			// XOR every byte
			decrypted[i] = (byte) (kp.key1[i] ^ kp.key2[i]);
		}
		return new String(decrypted);
	}

	public static void main(String[] args) {
		KeyPair kp = encrypt("One Time Pad!");
		String result = decrypt(kp);
		System.out.println(result);
	}
}
