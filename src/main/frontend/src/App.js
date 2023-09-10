import { useState } from 'react'
import Login from './components/Login'
import Header from './components/Header';
import Sessions from './components/Sessions';
import CreateRestaurant from './components/CreateRestaurant';

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
          {showCreateRestaurant && <CreateRestaurant />}
          <Sessions user={user} showAddSession={showAddSession} />
        </>
      )}
    </div>
  );
}

export default App;
