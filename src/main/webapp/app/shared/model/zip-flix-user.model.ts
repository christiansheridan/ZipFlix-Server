import { Moment } from 'moment';
import { IProfile } from 'app/shared/model//profile.model';

export interface IZipFlixUser {
    id?: string;
    dateOfBirth?: Moment;
    profiles?: IProfile[];
}

export class ZipFlixUser implements IZipFlixUser {
    constructor(public id?: string, public dateOfBirth?: Moment, public profiles?: IProfile[]) {}
}
