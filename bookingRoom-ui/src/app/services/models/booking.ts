/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { Room } from '../models/room';
import { User } from '../models/user';
export interface Booking {
  bookingConfirmationCode?: string;
  checkInDate?: string;
  checkOutDate?: string;
  id?: number;
  numOfAdults?: number;
  numOfChildren?: number;
  room?: Room;
  totalNumOfGuest?: number;
  user?: User;
}
