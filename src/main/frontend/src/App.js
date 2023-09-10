import { useState } from 'react'
import Login from './components/Login'

function App() {

  const [user, setUser] = useState(null)

  const handleLogin = (username) => {
    setUser(username);
  };

  return (
    <div className="container">
      {!user ? (
        <Login onLogin={handleLogin} />
      ) : (
        <>
          <h1>Welcome, {user}!</h1>
        </>
      )}
    </div>
  );
}

export default App;
