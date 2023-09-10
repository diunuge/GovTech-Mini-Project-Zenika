import React, { useState } from 'react';
import axios from "axios";

function Login({ onLogin }) {
  const [username, setUsername] = useState('');

  const [user, setUser] = useState(null)
  const [error, setError] = useState('');

  const fetchUserProfiles = async (username) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/v1/user/${username}`);

      if (response.status === 200) {
        setUser(response.data);
        setError('');
      }
    } catch (error) {
      setError('Error logging! Please try again {Diunuge, Buddhika, Wijesinge}');
      console.error('Error logging:', error);
      setUsername('');
    }
  };

  const performLogin = async () => {
    await fetchUserProfiles(username);

    if (user) {
      onLogin(user);
      setUsername('');
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(username);

    performLogin();
  };

  return (
    <div>
      <h1>Login</h1>
      <form className='add-form' onSubmit={handleSubmit}>
        <div className='form-control'>
          <input
            type="text"
            placeholder="Enter your username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <button className='btn btn-block' type="submit">Log In</button>
        </div>
      </form>
      <br></br>
      <p>{error}</p>
    </div>
  );
}

export default Login;