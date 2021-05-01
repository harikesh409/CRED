import { NavbarBrand } from 'react-bootstrap';
import { ReactComponent as Logo } from '../assets/images/logo.svg'

export function CustomNavBrand() {
    return (
        <NavbarBrand href="/">
            <Logo className="d-inline-block align-top"/>
            CRED
        </NavbarBrand>
    )
}