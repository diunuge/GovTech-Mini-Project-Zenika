import { useState, useEffect } from 'react'
import axios from "axios"

const AddRestaurant = ({ sessionId }) => {

  const [resList, setResList] = useState([])

  const [selectedRes, setSelectedRes] = useState('')

  const fetchRestaurants = () => {
    axios.get("http://localhost:8080/api/v1/restaurant").then(res => {
      console.log(res.data);
      setResList(res.data);
    });
  };

  useEffect ( () => {
    fetchRestaurants();
  }, []);

  const suggestRestaurant = async (id) => {
    console.log('Suggest restaurant for session', id)
    try {
      const response = await axios.post(
        `http://localhost:8080/api/v1/session/${id}/submit`,
        { id: selectedRes }
      );

      if (response.status === 201) {
        //TODO: fetchSessions
      }
    } catch (error) {
      console.error('Error suggesting restaurant:', error);
    }
  }

  const onSubmit = (e) => {
    e.preventDefault()

    if (!selectedRes) {
      alert('Please select a restaurant!')
      return
    }

    suggestRestaurant(sessionId)

    setSelectedRes('')
  }

  return (
    <form className='add-form' onSubmit={onSubmit}>
      <div className='form-control'>
        <label>Suggest restaurants: {selectedRes}</label>
        <select
          value={selectedRes}
          onChange={(e) => setSelectedRes(e.target.value)}
        >
          <option value="">Select a restaurant</option>
          {resList.map((res) => (
            <option key={res.id} value={res.id}>
              {res.name}
            </option>
          ))}
        </select>
      </div>

      <input type='submit' value='Save Session' className='btn btn-block' />
    </form>
  )
}

export default AddRestaurant
