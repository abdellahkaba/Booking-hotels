import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AdminComponent} from "./components/admin/admin.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {authGuard} from "../../services/guard/auth.guard";
import {ManageRoomComponent} from "./components/manage-room/manage-room.component";
import {ListRoomComponent} from "./components/list-room/list-room.component";


const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'manage-room',
        component: ManageRoomComponent,
        canActivate: [authGuard]
      },
    ]
  },
  {
    path: 'list-rooms',
    component: ListRoomComponent,
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [authGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class BookingRoomRoutingModule {
}
