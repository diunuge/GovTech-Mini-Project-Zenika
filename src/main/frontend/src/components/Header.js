import PropTypes from 'prop-types'
import Button from './Button'

const Header = ({user, title, onBtnSessionClick, onBtnRestaurantClick}) => {

  return (
    <header className='header'>
      <h3>Welcome {user.username}!</h3>
      <h2>{title}</h2>
      <div>
        <Button color='green' text='Create Session' onClick={onBtnSessionClick}/>
        <Button color='green' text='Create Restaurent' onClick={onBtnRestaurantClick}/>
      </div>
    </header>
  )
}

Header.defaultProps = {
    title: 'Event Planner',
}

Header.propTypes = {
    title: PropTypes.string,
    onClick: PropTypes.func,
}

export default Header