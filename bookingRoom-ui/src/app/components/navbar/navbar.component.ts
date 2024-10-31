import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {TokenService} from "../../services/token/token.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    NgIf
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent implements OnInit{
  isLoggedIn: boolean = false
  userRoles: string[] = []
  ngOnInit(): void {
    const linkColor = document.querySelectorAll('.nav-link');
    linkColor.forEach(link => {
      if (window.location.href.endsWith(link.getAttribute('href') || '')) {
        link.classList.add('active');
      }
      link.addEventListener('click', () => {
        linkColor.forEach(l => l.classList.remove('active'));
        link.classList.add('active');
      });
    });
  }

  constructor(
    private tokenService: TokenService
  ) {
    this.isLoggedIn = this.tokenService.isTokenValid()
    this.userRoles = this.tokenService.userRoles
  }

  //
  isAdmin(): boolean {
    return this.userRoles.includes('ADMIN');
  }

  logout(){
    localStorage.removeItem('token')
    window.location.reload()
  }


}
