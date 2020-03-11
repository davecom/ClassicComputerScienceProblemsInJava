// Governor.java
// From Classic Computer Science Problems in Java Chapter 6
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

package chapter6;

import java.util.ArrayList;
import java.util.List;

public class Governor extends DataPoint {
	private double longitude;
	private double age;
	private String state;

	public Governor(double longitude, double age, String state) {
		super(List.of(longitude, age));
		this.longitude = longitude;
		this.age = age;
		this.state = state;
	}

	@Override
	public String toString() {
		return state + ": (longitude: " + longitude + ", age: " + age + ")";
	}

	public static void main(String[] args) {
		List<Governor> governors = new ArrayList<>();
		governors.add(new Governor(-86.79113, 72, "Alabama"));
		governors.add(new Governor(-152.404419, 66, "Alaska"));
		governors.add(new Governor(-111.431221, 53, "Arizona"));
		governors.add(new Governor(-92.373123, 66, "Arkansas"));
		governors.add(new Governor(-119.681564, 79, "California"));
		governors.add(new Governor(-105.311104, 65, "Colorado"));
		governors.add(new Governor(-72.755371, 61, "Connecticut"));
		governors.add(new Governor(-75.507141, 61, "Delaware"));
		governors.add(new Governor(-81.686783, 64, "Florida"));
		governors.add(new Governor(-83.643074, 74, "Georgia"));
		governors.add(new Governor(-157.498337, 60, "Hawaii"));
		governors.add(new Governor(-114.478828, 75, "Idaho"));
		governors.add(new Governor(-88.986137, 60, "Illinois"));
		governors.add(new Governor(-86.258278, 49, "Indiana"));
		governors.add(new Governor(-93.210526, 57, "Iowa"));
		governors.add(new Governor(-96.726486, 60, "Kansas"));
		governors.add(new Governor(-84.670067, 50, "Kentucky"));
		governors.add(new Governor(-91.867805, 50, "Louisiana"));
		governors.add(new Governor(-69.381927, 68, "Maine"));
		governors.add(new Governor(-76.802101, 61, "Maryland"));
		governors.add(new Governor(-71.530106, 60, "Massachusetts"));
		governors.add(new Governor(-84.536095, 58, "Michigan"));
		governors.add(new Governor(-93.900192, 70, "Minnesota"));
		governors.add(new Governor(-89.678696, 62, "Mississippi"));
		governors.add(new Governor(-92.288368, 43, "Missouri"));
		governors.add(new Governor(-110.454353, 51, "Montana"));
		governors.add(new Governor(-98.268082, 52, "Nebraska"));
		governors.add(new Governor(-117.055374, 53, "Nevada"));
		governors.add(new Governor(-71.563896, 42, "New Hampshire"));
		governors.add(new Governor(-74.521011, 54, "New Jersey"));
		governors.add(new Governor(-106.248482, 57, "New Mexico"));
		governors.add(new Governor(-74.948051, 59, "New York"));
		governors.add(new Governor(-79.806419, 60, "North Carolina"));
		governors.add(new Governor(-99.784012, 60, "North Dakota"));
		governors.add(new Governor(-82.764915, 65, "Ohio"));
		governors.add(new Governor(-96.928917, 62, "Oklahoma"));
		governors.add(new Governor(-122.070938, 56, "Oregon"));
		governors.add(new Governor(-77.209755, 68, "Pennsylvania"));
		governors.add(new Governor(-71.51178, 46, "Rhode Island"));
		governors.add(new Governor(-80.945007, 70, "South Carolina"));
		governors.add(new Governor(-99.438828, 64, "South Dakota"));
		governors.add(new Governor(-86.692345, 58, "Tennessee"));
		governors.add(new Governor(-97.563461, 59, "Texas"));
		governors.add(new Governor(-111.862434, 70, "Utah"));
		governors.add(new Governor(-72.710686, 58, "Vermont"));
		governors.add(new Governor(-78.169968, 60, "Virginia"));
		governors.add(new Governor(-121.490494, 66, "Washington"));
		governors.add(new Governor(-80.954453, 66, "West Virginia"));
		governors.add(new Governor(-89.616508, 49, "Wisconsin"));
		governors.add(new Governor(-107.30249, 55, "Wyoming"));

		KMeans<Governor> kmeans = new KMeans<>(2, governors);
		List<KMeans<Governor>.Cluster> govClusters = kmeans.run(100);
		for (int clusterIndex = 0; clusterIndex < govClusters.size(); clusterIndex++) {
			System.out.println("Cluster " + clusterIndex + ": "
					+ govClusters.get(clusterIndex).points);
		}
	}

}
