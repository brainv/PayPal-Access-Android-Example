/*
 * Copyright 2012 PayPal.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paypal.example.android.ppaccess.helper;

import android.net.Uri;

/**
 * This class is used to enable fast usage of PayPal Access and OAuth 2.0.
 * 
 * @author tmesserschmidt@paypal.com
 * 
 */
public class AccessHelperOAuth extends AccessHelper {
	public static final String	TOKEN_URL			= URL_REDIRECT + "?code=";
	private static final String	URL_AUTHORIZE		= "https://identity.x.com/xidentity/resources/authorize";
	private static final String	URL_TOKENSERVICE	= "https://identity.x.com/xidentity/oauthtokenservice";
	private static final String	URL_PROFILE			= "https://identity.x.com/xidentity/resources/profile/me";

	/**
	 * Initializes an instance of AccessHelper and returns it.
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @return the AccessHelper
	 */
	public static AccessHelperOAuth init(final String clientId,
			final String clientSecret) {
		return new AccessHelperOAuth(clientId, clientSecret);
	}

	/**
	 * Not going to be exposed.
	 * 
	 * @param clientId
	 * @param clientSecret
	 */
	private AccessHelperOAuth(final String clientId, final String clientSecret) {
		super(clientId, clientSecret);
	}

	/**
	 * Returns the application's authorization URL for PayPal Access.
	 * 
	 * @return the authorization URL as {@link String}
	 */
	public String getAuthUrl() {
		final StringBuilder authUrlBuilder = new StringBuilder();
		authUrlBuilder.append(URL_AUTHORIZE).append("?")
				.append(PARAM_CLIENT_ID).append(valueClientId).append("&")
				.append(PARAM_SCOPE).append(URL_PROFILE).append("&")
				.append(PARAM_REDIRECT_URI).append(Uri.encode(URL_REDIRECT))
				.append("&").append(PARAM_RESPONSE_TYPE)
				.append(VALUE_RESPONSE_TYPE);
		return authUrlBuilder.toString();
	}

	/**
	 * Returns the Access Token url.
	 * 
	 * @return the Access Token url
	 */
	public String getTokenServiceUrl() {
		return URL_TOKENSERVICE;
	}

	/**
	 * Creates the needed parameters to get the Authorization Token.
	 * 
	 * @param code
	 *            the code from the Token Service
	 * @return the needed parameters
	 */
	public String getTokenServiceParameters(final String code) {
		final StringBuilder paramsBuilder = new StringBuilder();
		paramsBuilder.append(PARAM_CLIENT_ID).append(valueClientId).append("&")
				.append(PARAM_REDIRECT_URI).append(Uri.encode(URL_REDIRECT))
				.append("&").append(PARAM_GRANT_TYPE).append("&")
				.append(PARAM_CLIENT_SECRET).append(valueClientSecret)
				.append("&").append(PARAM_CODE).append(code);
		return paramsBuilder.toString();
	}

	/**
	 * Returns the URL for requesting profile information.
	 * 
	 * @param accessToken
	 * @return the profile url including the Access Token
	 */
	public String getProfileUrl(final String accessToken) {
		final StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(URL_PROFILE).append("?").append(PARAM_OAUTH_TOKEN)
				.append(accessToken);
		return urlBuilder.toString();
	}

	/**
	 * Returns the URL which can be converted to an URI to extract the access
	 * code
	 * 
	 * @return the callback URL
	 */
	public String getAccessCodeUrl() {
		return TOKEN_URL;
	}
}
