/********************************************************************************
 The MIT License(MIT)
 
 Copyright(c) 2016 Copyleaks LTD (https://copyleaks.com)
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sub-license, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
********************************************************************************/

package com.example.classdream;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class CopyleaksCloud
{
	private LoginToken Token;
	private eProduct Product;
    Context c;

	public CopyleaksCloud(eProduct selectedProduct, Context g)
	{
		c = g ;
		setProduct(selectedProduct);
	}
	protected eProduct getProduct()
	{
		return this.Product;
	}
	private void setProduct(eProduct product)
	{
		this.Product = product;
	}
	protected LoginToken getToken()
	{
		return this.Token;
	}
	public void setToken(LoginToken token)
	{
		this.Token = token;
	}

	public int getCredits()
	{
		LoginToken.ValidateToken(this.getToken());
		return UserAuthentication.getCreditBalance(this.getToken(), this.getProduct());
	}
	public void Login(String email, String APIKey) throws IOException
	{
		this.setToken(UserAuthentication.Login(email, APIKey,c));
	}



	protected String productToServicePage()
	{
		switch (this.getProduct())
		{
			case Businesses:
				return Settings.BusinessesServicePage;
			case Academic:
				return Settings.AcademicServicePage;
			case Websites:
				return Settings.WebsitesServicePage;
			default:
				throw new RuntimeException("Unknown service page.");
		}
	}
	
	public  SupportedFiles SupportedFiles()
{
		String json;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		URL reqUrl;
		HttpURLConnection conn = null;
		try
		{
			reqUrl = new URL(String.format("%1$s/%2$s/miscellaneous/supported-file-types", Settings.ServiceEntryPoint, Settings.ServiceVersion));
			conn = CopyleaksClient.getClient(reqUrl, RequestMethod.GET, HttpContentTypes.Json, HttpContentTypes.Json);

			if (conn.getResponseCode() != 200)
				Toast.makeText(c," CopyClod 4"+conn.getResponseCode(),Toast.LENGTH_LONG).show();


			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream()))
			{
				json = HttpURLConnectionHelper.convertStreamToString(inputStream);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}

		if (json == null || json.isEmpty())
			throw new RuntimeException("Unable to process server response.");

		SupportedFiles response = gson.fromJson(json, SupportedFiles.class);
		return response;
	}
	
	public  String[] SupportedOcrLanguages()
	{
		String json;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		URL reqUrl;
		HttpURLConnection conn = null;
		try
		{
			reqUrl = new URL(String.format("%1$s/%2$s/miscellaneous/ocr-languages-list", Settings.ServiceEntryPoint, Settings.ServiceVersion));
			conn = CopyleaksClient.getClient(reqUrl, RequestMethod.GET, HttpContentTypes.Json, HttpContentTypes.Json);

			if (conn.getResponseCode() != 200)
				Toast.makeText(c," CopyClod 5"+conn.getResponseCode(),Toast.LENGTH_LONG).show();


			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream()))
			{
				json = HttpURLConnectionHelper.convertStreamToString(inputStream);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}

		if (json == null || json.isEmpty())
			Toast.makeText(c,"Unable to process server response.",Toast.LENGTH_LONG).show();


		return gson.fromJson(json, String[].class);
	}
	public CopyleaksProcess CreateByUrl(URI url, ProcessOptions options)
	{
		LoginToken.ValidateToken(this.getToken()); // Token Validation

		String json = null;
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
		CreateCommandRequest model = new CreateCommandRequest();
		model.setURL(url.toString());
		URL reqUrl;
		HttpURLConnection conn = null;
		try
		{
			reqUrl = new URL(String.format("%1$s/%2$s/%3$s/create-by-url", Settings.ServiceEntryPoint,
					Settings.ServiceVersion, this.productToServicePage()));
			conn = CopyleaksClient.getClient(reqUrl, this.getToken(), RequestMethod.POST, HttpContentTypes.Json,
					HttpContentTypes.Json);

			if (options != null)
				options.addHeaders(conn);

			CopyleaksClient.HandleString.attach(conn, gson.toJson(model));

			if (conn.getResponseCode() != 200)
			{
				System.out.println("Responce Null");
//				throw new CommandFailedException(conn);
			}

			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream()))
			{
				json = HttpURLConnectionHelper.convertStreamToString(inputStream);
				System.out.println("Responce "+json);
			}
		}
		catch (Exception e)
		{
			System.out.println("Responce Null"+e);

		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}

		if (json == null || json.isEmpty())
			System.out.println("JSON NULL");

		CreateResourceResponse response = gson.fromJson(json, CreateResourceResponse.class);

		System.out.println("JSON RESULT"+response.getProcessId());

		return new CopyleaksProcess(productToServicePage(), this.getToken(), response, response.getCustomFields());
	}

}
