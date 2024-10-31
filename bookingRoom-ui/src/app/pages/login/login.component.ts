import {Component, OnInit} from '@angular/core';
import {AuthenticationRequest} from "../../services/models/authentication-request";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {AuthenticationService} from "../../services/services/authentication.service";
import {AuthenticationResponse} from "../../services/models/authentication-response";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {TokenService} from "../../services/token/token.service";



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{
  authRequest: AuthenticationRequest = {
    email: '',
    password: ''
  }

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService,
    private toastr: ToastrService,
  ) {
  }
  ngOnInit(): void {
  }

  login() {
    this.authService.login({
      body: this.authRequest
    }).subscribe({
      next: (res: AuthenticationResponse) => {
        this.tokenService.token = res.token as string
        this.router.navigate([''])
      },
      error: (err) => {
        console.log(err);
        if (err.error.validationErrors) {
          this.toastr.error(err.error.validationErrors, 'Oups !' )
        } else {
          this.toastr.error(err.error.error, 'Oups !')
        }
      }
    });
  }

  register (){
    this.router.navigate(['register']);
  }
}
