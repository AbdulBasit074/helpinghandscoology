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
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class CopyleaksProcess implements Comparable<CopyleaksProcess>, Serializable
{

	private static final long serialVersionUID = 1L;

	public UUID PID;

	public UUID getPID()
	{
		return PID;
	}

	private void setPID(UUID processId)
	{
		PID = processId;
	}

	private Date CreationTimeUTC;

	/**
	 * Get the process creation time
	 *
	 * @return Process creation time
	 */
	public Date getCreationTimeUTC()
	{
		return CreationTimeUTC;
	}

	private void setCreationTimeUTC(Date creationTimeUTC)
	{
		CreationTimeUTC = creationTimeUTC;
	}

	private boolean ListProcesses_IsCompleted = false;

	private HashMap<String, String> CustomFields;

	public HashMap<String, String> getCustomFields()
	{
		return CustomFields;
	}

	private void setCustomFields(HashMap<String, String> value)
	{
		this.CustomFields = value;
	}

	private LoginToken SecurityToken;

	private LoginToken getSecurityToken()
	{
		return SecurityToken;
	}

	private void setSecurityToken(LoginToken securityToken)
	{
		SecurityToken = securityToken;
	}

	CopyleaksProcess(String product, LoginToken authorizationToken, ProcessInList process)
	{
		setProduct(product);
		this.setPID(process.getProcessId());
		this.setCreationTimeUTC(process.getCreationTimeUTC());
		this.setSecurityToken(authorizationToken);
		this.setCustomFields(process.getCustomFields());
		this.ListProcesses_IsCompleted = process.getStatus().equalsIgnoreCase("finished");
	}

	CopyleaksProcess(String product, LoginToken authorizationToken, CreateResourceResponse response,HashMap<String, String> customFields)
	{
		setProduct(product);
		this.setPID(response.getProcessId());
		this.setCreationTimeUTC(response.getCreationTimeUTC());
		this.setSecurityToken(authorizationToken);
		this.setCustomFields(customFields);
	}

	private String Product;

	protected String getProduct()
	{
		return this.Product;
	}

	private void setProduct(String product)
	{
		this.Product = product;
	}


	public int getCurrentProgress()
	{
		if (this.ListProcesses_IsCompleted)
		{
			return 100;
		}

		LoginToken.ValidateToken(this.getSecurityToken());

		URL url;
		HttpURLConnection conn = null;
		Gson gson = new GsonBuilder().create();
		String json = null;
		try
		{
			url = new URL(String.format("%1$s/%2$s/%3$s/%4$s/status", Settings.ServiceEntryPoint,
					Settings.ServiceVersion, this.getProduct(), getPID()));
			conn = CopyleaksClient.getClient(url, this.getSecurityToken(), RequestMethod.GET, HttpContentTypes.Json,
					HttpContentTypes.TextPlain);

			if (conn.getResponseCode() != 200)
			{

			}
				//throw new CommandFailedException(conn);

			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream()))
			{
				json = HttpURLConnectionHelper.convertStreamToString(inputStream);
				System.out.println("JASON"+json);
			}
		}
		catch (Exception e)
		{
			System.out.println("Error"+e);
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}
		CheckStatusResponse response = gson.fromJson(json, CheckStatusResponse.class);
		System.out.println("JSON NULL"+response.getProgressPercents());
		return response.getProgressPercents();
	}


	public ResultRecord[] GetResults(Context g)
	{
		LoginToken.ValidateToken(this.getSecurityToken());
		System.out.println("Scanning... 2");

		String json = null;
		URL url;
		HttpURLConnection conn = null;
		Gson gson = new GsonBuilder().create();
		try
		{
			url = new URL(String.format("%1$s/%2$s/%3$s/%4$s/result", Settings.ServiceEntryPoint,
					Settings.ServiceVersion, this.getProduct(), getPID()));
			conn = CopyleaksClient.getClient(url, this.getSecurityToken(), RequestMethod.GET, HttpContentTypes.Json,
					HttpContentTypes.Json);

			if (conn.getResponseCode() != 200)
			{

			}
			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream()))
			{
				json = HttpURLConnectionHelper.convertStreamToString(inputStream);
				System.out.println("JSON NULL"+json);
			}
			catch (Exception e)
			{
				System.out.println("Error"+e);
			}
		}
		catch (Exception e)
		{
			System.out.println("Error"+e);
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}
		if (json==null)
		{
			System.out.println("JSON NULL");
		}
		ResultRecord[] results = gson.fromJson(json, ResultRecord[].class);
		if(results==null)
		{
			System.out.println("RESULT NULL");
		}
		System.out.println("RESULT NULL"+results);
		//Arrays.sort(results, Collections.reverseOrder());
		return results;
	}


	public void Delete()
	{
		LoginToken.ValidateToken(this.getSecurityToken());

		URL url;
		HttpURLConnection conn = null;
		try
		{
			url = new URL(String.format("%1$s/%2$s/%3$s/%4$s/delete", Settings.ServiceEntryPoint,
					Settings.ServiceVersion, this.getProduct(), this.PID));
			conn = CopyleaksClient.getClient(url, this.getSecurityToken(), RequestMethod.DELETE, HttpContentTypes.Json,
					HttpContentTypes.Json);
			if (conn.getResponseCode() != 200) {
				//throw new CommandFailedException(conn);
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
	}

	public String DownloadSourceText()
	{
		LoginToken.ValidateToken(this.getSecurityToken());

		URL url;
		HttpURLConnection conn = null;
		try
		{
			url = new URL(String.format("%1$s/%2$s/%3$s/source-text?pid=%4$s", Settings.ServiceEntryPoint,
					Settings.ServiceVersion, Settings.DownloadsServicePage, getPID()));
			conn = CopyleaksClient.getClient(url, this.getSecurityToken(), RequestMethod.GET, HttpContentTypes.Json,
					HttpContentTypes.Json);

			if (conn.getResponseCode() != 200) {
				//throw new CommandFailedException(conn);
			}
			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream()))
			{
				return HttpURLConnectionHelper.convertStreamToString(inputStream);
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
	}
	
	public String DownloadResultText(ResultRecord result)

	{
		LoginToken.ValidateToken(this.getSecurityToken());

		URL url;
		HttpURLConnection conn = null;
		try
		{
			url = new URL(result.getCachedVersion());
			conn = CopyleaksClient.getClient(url, this.getSecurityToken(), RequestMethod.GET, HttpContentTypes.Json,
					HttpContentTypes.Json);

			if (conn.getResponseCode() != 200) {//throw new CommandFailedException(conn);
			}

			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream()))
			{
				return HttpURLConnectionHelper.convertStreamToString(inputStream);
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
	}
	
	public ComparisonResult DownloadResultComparison(ResultRecord result)
	{
		LoginToken.ValidateToken(this.getSecurityToken());
		String json=null;
		URL url;
		HttpURLConnection conn = null;
		Gson gson = new GsonBuilder().create();
		try
		{
			url = new URL(result.getComparisonReport());
			conn = CopyleaksClient.getClient(url, this.getSecurityToken(), RequestMethod.GET, HttpContentTypes.Json,
					HttpContentTypes.Json);

			if (conn.getResponseCode() != 200) {
				//throw new CommandFailedException(conn);
			}
			try (InputStream inputStream = new BufferedInputStream(conn.getInputStream()))
			{
				json = HttpURLConnectionHelper.convertStreamToString(inputStream);
				System.out.println(""+json);
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR NULL"+e);
		}
		finally
		{
			if (conn != null)
				conn.disconnect();
		}
		if(json==null)
		{
			System.out.println("JSON NULL");
		}
		else
		{
			System.out.println("Json Found"+json);
		}



		return gson.fromJson(json, ComparisonResult.class);
	}

	@Override
	public String toString()
	{
		return this.getPID().toString();
	}

	@Override
	public int compareTo(CopyleaksProcess process)
	{
		return this.getCreationTimeUTC().compareTo(process.CreationTimeUTC);
	}
}
