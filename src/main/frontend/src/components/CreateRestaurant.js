import { useState } from 'react'
import axios from "axios"

const CreateRestaurant = (showCreateRestaurant) => {

  const [restaurantName, setRestaurantName] = useState('')

  const createResturant = async () => {
    console.log('Add Restaurant', restaurantName)

    try {
      const response = await axios.post(
        'http://localhost:8080/api/v1/restaurant',
        {name: restaurantName}
      );

      if (response.status === 201) {
        console.log('Restaurant Added!');
      }
    } catch (error) {
      console.error('Error adding session:', error);
    }
  }

  const onSubmit = (e) => {
    e.preventDefault()

    if (!restaurantName) {
      alert('Please add restaurant name!')
      return
    }

    createResturant()

    setRestaurantName('')
  }

  return (
    <form className='add-form' onSubmit={onSubmit}>
      <div className='form-control'>
        <label>Restaurant Name</label>
        <input
          type='text'
          placeholder='Tea Palace'
          value={restaurantName}
          onChange={(e) => setRestaurantName(e.target.value)}
        />
      </div>

      <input type='submit' value='Save Restaurant' className='btn btn-block' />
    </form>
  )
}

export default CreateRestaurant