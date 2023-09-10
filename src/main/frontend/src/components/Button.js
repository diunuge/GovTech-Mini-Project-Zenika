import PropTypes from 'prop-types'

const Button = ({color, text, onClick, enabled}) => {

  return (
    <button 
      onClick={onClick} 
      style={{backgroundColor: enabled ? color : 'gray'}} 
      className='btn'
      disabled = {!enabled}
    >
      {text}
    </button>
  )
}

Button.defaultProps = {
    color : 'steelBlue',
    text : 'button',
    enabled : true,
}

Button.propTypes = {
    color : PropTypes.string,
    text : PropTypes.string,
    onClick : PropTypes.func,
    enabled : PropTypes.bool
}

export default Button