import { useState } from 'react'
import Login from './components/Login'
import Header from './components/Header';

function App() {

  const [user, setUser] = useState(null)
  const [showAddSession, setShowAddSession] = useState(false)
  const [showCreateRestaurant, setShowCreateRestaurant] = useState(false)

  const handleLogin = (user) => {
    setUser(user);
  };

  return (
    <div className="container">
      {!user ? (
        <Login onLogin={handleLogin} />
      ) : (
        <>
          <Header
            user = {user}
            onBtnSessionClick={() => setShowAddSession(!showAddSession)}
            onBtnRestaurantClick={() => setShowCreateRestaurant(!showCreateRestaurant)}
          />
        </>
      )}
    </div>
  );
}

export default App;
