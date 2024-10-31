import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/services/authentication.service";
import {RegistrationRequest} from "../../services/models/registration-request";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  registerRequest: RegistrationRequest = {
    name: '',
    email: '',
    phone: '',
    password: ''
  }
  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private toastr: ToastrService
  ) {
  }

  register() {
    this.authService.register({
      body: this.registerRequest
    }).subscribe({
      next: () => {
        this.router.navigate(['login'])
      },
      error: (err) => {
       console.log(err)
        if (err.error.validationErrors) {
          this.toastr.error(err.error.validationErrors, 'Oups !')
        }else {
          this.toastr.error(err.error.error, 'Oups !')
        }
      }
    });
  }
  login() {
    this.router.navigate(['login'])
  }
}
