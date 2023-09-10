import PropTypes from 'prop-types'
import { useState } from 'react'
import { FaTimes } from 'react-icons/fa'
import Button from './Button'
import AddRestaurant from './AddRestaurant'

const Session = ({ session, user, onDelete, onJoin, onEnd }) => {

  const [showAddRestaurant, setAddRestaurant] = useState(false)

  return (
    <div className='task'>
      <h3>
        [{session.id}] {' '} {session.name}
        <div>
          <Button
            text='Add Restaurent'
            onClick={() => setAddRestaurant(!showAddRestaurant)}
            enabled={!session.closed}
          />
          <Button
            text='Join'
            onClick={() => onJoin(session.id)}
            enabled={!session.closed}
          />
          <Button
            text='Close'
            onClick={() => onEnd(session.id)}
            enabled={!session.closed && user.id===session.initiator.id}
          />
          <FaTimes
            style={{ color: 'red', cursor: 'pointer' }}
            onClick={() => onDelete(session.id)}
          />
        </div>


      </h3>
      <p>Participants :{session.participants.map(user => user.username).join(', ')}</p>
      <p>Invited :{session.participantsInvited.map(user => user.username).join(', ')}</p>
      <p>Submitted Restaurants :{session.submittedRestaurants.map(user => user.name).join(', ')}</p>
      <p>Selected Restaurant :{session.selectedRestaurant ? session.selectedRestaurant.name : ''}</p>
      <p>Initiated by :{session.initiator.username}</p>

      {showAddRestaurant && <AddRestaurant sessionId={session.id}/>}
    </div>
  )
}

Session.propTypes = {
  onDelete: PropTypes.func,
  onJoin: PropTypes.func,
  onEnd: PropTypes.func,
}

export default Session