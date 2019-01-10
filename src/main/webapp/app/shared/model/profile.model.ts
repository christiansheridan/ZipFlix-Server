import { Moment } from 'moment';
import { ITag } from 'app/shared/model//tag.model';
import { IZipFlixUser } from 'app/shared/model//zip-flix-user.model';

export interface IProfile {
    id?: string;
    name?: string;
    dateOfBirth?: Moment;
    imageURL?: string;
    interests?: ITag[];
    zipFlixUser?: IZipFlixUser;
}

export class Profile implements IProfile {
    constructor(
        public id?: string,
        public name?: string,
        public dateOfBirth?: Moment,
        public imageURL?: string,
        public interests?: ITag[],
        public zipFlixUser?: IZipFlixUser
    ) {}
}
