package com.tqs.trabalho1.model;

public class CacheStatObject {
	
	private int external_api_access;
	private int n_times_cached;
	private int failed_api_access;
	
	
	public CacheStatObject(int external_api_access, int n_times_cached, int failed_api_access) {
		this.external_api_access = external_api_access;
		this.n_times_cached = n_times_cached;
		this.failed_api_access = failed_api_access;
	}


	public int getExternal_api_access() {
		return external_api_access;
	}


	public void setExternal_api_access(int external_api_access) {
		this.external_api_access = external_api_access;
	}


	public int getN_times_cached() {
		return n_times_cached;
	}


	public void setN_times_cached(int n_times_cached) {
		this.n_times_cached = n_times_cached;
	}


	public int getFailed_api_access() {
		return failed_api_access;
	}


	public void setFailed_api_access(int failed_api_access) {
		this.failed_api_access = failed_api_access;
	}
	

}
