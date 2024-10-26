import {Component, OnInit} from '@angular/core';
import {PageResponseRoomResponse} from "../../../../services/models/page-response-room-response";
import {RoomService} from "../../../../services/services/room.service";
import {ToastrService} from "ngx-toastr";
import {NgForOf, NgIf} from "@angular/common";
import {NavbarComponent} from "../../../../components/navbar/navbar.component";
import {FooterComponent} from "../../../../components/footer/footer.component";
import {TokenService} from "../../../../services/token/token.service";

@Component({
  selector: 'app-list-room',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    NavbarComponent,
    FooterComponent
  ],
  templateUrl: './list-room.component.html',
  styleUrl: './list-room.component.scss'
})
export class ListRoomComponent implements OnInit{

  userRoles: string[] = []

  roomResponse: PageResponseRoomResponse = {};
  page = 0;
  size = 3;
  pages: any = [];

  constructor(
    private roomService: RoomService,
    private tokenService: TokenService,
    private toastr: ToastrService
  ) {
    this.userRoles = this.tokenService.userRoles
  }
  ngOnInit(): void {
    this.getAllRooms();
  }

  isAdmin(): boolean {
    return this.userRoles.includes('ADMIN')
  }
  private getAllRooms() {
    this.roomService.getAllRooms({
      page: this.page,
      size: this.size
    })
      .subscribe({
        next: (rooms) => {
          this.roomResponse = rooms;
        }
      })
  }

  goToPage(page: number){
    this.page = page;
    this.getAllRooms();
  }

  goToFirstPage(){
    this.page = 0;
    this.getAllRooms();
  }

  goToPreviousPage(){
    this.page --;
    this.getAllRooms();
  }

  goToLastPage(){
    this.page = this.roomResponse.totalPages as number -1;
    this.getAllRooms();
  }

  goToNextPage(){
    this.page ++;
    this.getAllRooms();
  }

  get isLastPage(){
    return this.page === this.roomResponse.totalPages as number -1;
  }

}
