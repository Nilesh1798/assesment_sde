import React, { useEffect, useState } from 'react';
import api from '../utils/api'; // Axios instance

function TrafficDashboard() {
  const [ipData, setIpData] = useState([]);
  const [hourlyData, setHourlyData] = useState([]);

  useEffect(() => {
    // Fetch IP data
    api
      .get('/traffic/ip')
      .then((response) => setIpData(response.data))
      .catch((error) => console.error('Error fetching IP data:', error));

    // Fetch hourly data
    api
      .get('/traffic/hourly')
      .then((response) => setHourlyData(response.data))
      .catch((error) => console.error('Error fetching hourly data:', error));
  }, []);

  return (
    <div>
      <h1>Traffic Dashboard</h1>
      <h2>Top IP Addresses</h2>
      <ul>
        {ipData.map((ip, index) => (
          <li key={index}>{ip}</li>
        ))}
      </ul>
      <h2>Hourly Traffic</h2>
      <ul>
        {hourlyData.map((hour, index) => (
          <li key={index}>{hour}</li>
        ))}
      </ul>
    </div>
  );
}

export default TrafficDashboard;
