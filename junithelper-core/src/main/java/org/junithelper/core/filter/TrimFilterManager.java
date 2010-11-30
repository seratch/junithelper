/* 
 * Copyright 2009-2010 junithelper.org. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */
package org.junithelper.core.filter;

import java.util.ArrayList;
import java.util.List;

public class TrimFilterManager {

	private List<TrimFilter> filters = new ArrayList<TrimFilter>();

	public List<TrimFilter> getFilters() {
		return this.filters;
	}

	public void removeFilter(Class<?> filterClass) {
		for (TrimFilter filter : filters) {
			if (filter.getClass().equals(filterClass)) {
				filters.remove(filter);
				break;
			}
		}
	}

	public void addFilter(TrimFilter... filters) {
		for (TrimFilter filter : filters) {
			this.filters.add(filter);
		}
	}

	public String doTrimAll(String src) {
		String dest = src;
		for (TrimFilter filter : filters) {
			dest = filter.trimAll(dest);
		}
		return dest;
	}

}
