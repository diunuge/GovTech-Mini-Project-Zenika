import { useState, useEffect } from 'react'
import axios from "axios"

const AddSession = ({onAdd}) => {

  const [sessionName, setSessionName] = useState('')
  const [sessionParticipants, setSessionParticipants] = useState([])
  const [selectedParticipant, setSelectedParticipant] = useState('')
  const [usersList, setUserList] = useState([])

  const fetchUsers = () => {
    axios.get("http://localhost:8080/api/v1/user").then(res => {
      console.log(res.data); 
      setUserList(res.data);
    });
  };

  const onParticipantSelection = (username) => {
    setSelectedParticipant(username);
    let user = usersList.find((user) => user.username === username)
    setSessionParticipants([...sessionParticipants, user])
    console.log("Selected Participants", selectedParticipant)
  }

  const onSubmit = (e) => {
    e.preventDefault()

    if(!sessionName){
      alert('Please add session name!')
      return
    }

    onAdd(sessionName, sessionParticipants)


    setSessionName('')
    setSelectedParticipant('');
    setSessionParticipants([])
  }

  useEffect ( () => {
    fetchUsers();
  }, []);

  return (
    <form className='add-form' onSubmit={onSubmit}>
      <div className='form-control'>
        <label>Session Name</label>
        <input 
          type='text' 
          placeholder='Monthly team meeting' 
          value={sessionName}
          onChange={(e) => setSessionName(e.target.value)}
        />
        <label>Invite participants: {sessionParticipants.map(user => user.username).join(', ')}</label>
        <select
          value={selectedParticipant} 
          onChange={(e) => onParticipantSelection(e.target.value)}
        >
          <option value="">Select a participant</option>
          {usersList.map((user) => (
            <option key={user.id} value={user.username}>
              {user.username}
            </option>
          ))}
        </select>
      </div>

      <input type='submit' value='Save Session' className='btn btn-block'/>
    </form>
  )
}

export default AddSession