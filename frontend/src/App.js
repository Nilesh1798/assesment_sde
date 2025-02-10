import React from 'react';
import TrafficDashboard from './components/TrafficDashboard';
import './App.css'; 

function App() {
  return (
    <div className="App">
      <header>
        <h1>Traffic Monitoring Dashboard</h1>
      </header>
      <main>
        <TrafficDashboard />
      </main>
      <footer>
        <p>© 2025 Dashboard Project. All rights reserved.</p>
      </footer>
    </div>
  );
}

export default App;
