import PropTypes from 'prop-types'
import { useState, useEffect } from 'react'
import axios from "axios"
import Session from './Session'
import AddSession from './AddSession';

const Sessions = ({ user, showAddSession }) => {

  const [sessions, setSessions] = useState([])

  const fetchSessions = () => {
    axios.get(`http://localhost:8080/api/v1/session/forUser/${user.id}`).then(res => {
      console.log(res);
      setSessions(res.data);
    });
  };

  const deleteSession = (id) => {
    console.log('Delete Session', id, '[Only in FE]')
    setSessions(sessions.filter((session) => session.id !== id))
  }

  const addSession = async (sessionName, sessionParticipants) => {

    const newSession = {
      name: sessionName,
      initiator: user,
      participantsInvited: sessionParticipants
    };

    console.log('Add Session', newSession)

    try {
      const response = await axios.post('http://localhost:8080/api/v1/session', newSession);

      if (response.status === 201) {
        fetchSessions();
      }
    } catch (error) {
      console.error('Error adding session:', error);
    }
  }

  const joinSession = async (id) => {
    console.log('Join to Session ', id)
    try {
      const response = await axios.post(
        `http://localhost:8080/api/v1/session/${id}/join`,
        user
      );

      if (response.status === 201) {
        fetchSessions();
      }
    } catch (error) {
      console.error('Error joining session:', error);
    }
  }

  const endSession = async (id) => {
    console.log('End Session ', id)
    try {
      const response = await axios.post(
        `http://localhost:8080/api/v1/session/${id}/end?userId=${user.id}`,
      );

      if (response.status === 201) {
        fetchSessions();
      }
    } catch (error) {
      console.error('Error ending session:', error);
    }
  }

  useEffect(() => {
    fetchSessions();
  }, []);

  return (
    <>
      {showAddSession && <AddSession onAdd={addSession} />}
      {
        sessions.length > 0 ?
          sessions.map((session) => (
            <Session
              key={session.id}
              session={session}
              user={user}
              onDelete={deleteSession}
              onJoin={joinSession}
              onEnd={endSession}
            />
          )) : 'No sessions to show'
      }
    </>
  )
}

Sessions.propTypes = {
  showAddSession : PropTypes.bool
}

export default Sessions